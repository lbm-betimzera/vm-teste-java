package vm.vmjavateste.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.java.teste.vmjavateste.VmJavaTesteApplication;
import com.vm.java.teste.vmjavateste.controller.dto.UsuarioDTO;
import com.vm.java.teste.vmjavateste.model.entity.Usuario;
import com.vm.java.teste.vmjavateste.model.repository.IUsuarioRepository;
import com.vm.java.teste.vmjavateste.service.EmailService;
import com.vm.java.teste.vmjavateste.service.UsuarioService;
import com.vm.java.teste.vmjavateste.service.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = VmJavaTesteApplication.class)
@AutoConfigureMockMvc
public class UsuarioServiceTest {

    @MockBean
    private EmailService emailService;

    @MockBean
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String token = "Bearer 404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*
    Teste generico pra testar o service sem uso de API
     */
    @Test
    public void testCriaUsuario() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Nome");
        usuarioDTO.setEmail("email@example.com");
        usuarioDTO.setSenha("senha");

        Usuario usuario = new Usuario(1L, "Nome", "email@example.com", "senha");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioDTO usuarioCriado = usuarioService.criaUsuario(usuarioDTO);

        assertEquals(usuarioDTO.getNome(), usuarioCriado.getNome());
        assertEquals(usuarioDTO.getEmail(), usuarioCriado.getEmail());
    }

    /*
    Implementado teste para criar usuário via api
     */
    @Test
    public void testCriaUsuarioAPI() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Dev 3");
        usuarioDTO.setEmail("teste_dev_3@teste.com");
        usuarioDTO.setSenha("senha_teste");

        Usuario usuario = new Usuario(1L, "Dev 3", "teste_dev_3@teste.com", "senha_teste");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);


        mockMvc.perform(post("/api/usuarios/cria-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO))
                        .header("Authorization", token))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("message").value("Usu�rio Cadastrado com sucesso"));
    }

    /*
    Teste para verificar que o usuário sem token não cria usuário
     */
    @Test
    public void testCriaUsuarioSemTokenAPI() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Dev 5");
        usuarioDTO.setEmail("teste_dev_5@teste.com");
        usuarioDTO.setSenha("senha_teste");

        Usuario usuario = new Usuario(1L, "Dev 3", "teste_dev_5@teste.com", "senha_teste");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);


        mockMvc.perform(post("/api/usuarios/cria-usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Unauthorized: Invalid Secret Key"));
    }


    /*
    Teste para dar get no usuário por ID
     */
    @Test
    public void testGetUsuarioAPI() throws Exception {
        Usuario usuario = new Usuario(1L, "Dev 1", "email_dev@teste.com", "senha");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/{id}", 1L)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.nome").value("Dev 1"))
                .andExpect(jsonPath("$.data.email").value("email_dev@teste.com"));
    }

}