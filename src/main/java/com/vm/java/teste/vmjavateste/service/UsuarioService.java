package com.vm.java.teste.vmjavateste.service;

import com.vm.java.teste.vmjavateste.service.enums.TipoEnvioEmail;
import com.vm.java.teste.vmjavateste.service.exception.SenhaObrigatoriaException;
import com.vm.java.teste.vmjavateste.service.exception.UsuarioExistenteException;
import com.vm.java.teste.vmjavateste.service.exception.UsuarioNotFoundException;
import com.vm.java.teste.vmjavateste.service.mapper.UsuarioMapper;
import com.vm.java.teste.vmjavateste.model.repository.IUsuarioRepository;
import com.vm.java.teste.vmjavateste.controller.dto.UsuarioDTO;
import com.vm.java.teste.vmjavateste.controller.dto.UsuarioFiltro;
import com.vm.java.teste.vmjavateste.model.entity.Usuario;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository IUsuarioRepository;

    private final UsuarioMapper usuarioMapper;

    private final EmailService emailService;


    @Transactional
    public UsuarioDTO criaUsuario(UsuarioDTO usuarioDTO) {

        Usuario novaEntidade = usuarioMapper.usuarioDtoParaEntidade(usuarioDTO);

        validaUsuarioAntesSalvar(novaEntidade);

        Usuario usuarioSalvo = IUsuarioRepository.save(novaEntidade);

        emailService.enviaEmail(usuarioSalvo.getEmail(), usuarioSalvo.getNome(), TipoEnvioEmail.CRIACAO);

        return usuarioMapper.usuarioParaDto(usuarioSalvo);
    }

    private void validaUsuarioAntesSalvar(Usuario usuario) {

        if(Objects.isNull(usuario)){
            throw new UsuarioNotFoundException("Usuario não pode estar vazio, preencha os campos obrigatorios ");
        }

        if (IUsuarioRepository.existsByEmail(usuario.getEmail()) ) {
            throw new UsuarioExistenteException();
        }

        if (usuario.getSenha()==null) {
            throw new SenhaObrigatoriaException();
        }
    }

    public UsuarioDTO editaUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = IUsuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado com o id: " + id));

        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        Usuario usuarioAtualizado = IUsuarioRepository.save(usuario);

        emailService.enviaEmail(usuarioAtualizado.getEmail(), usuarioAtualizado.getNome(), TipoEnvioEmail.ALTERACAO);
        return usuarioMapper.usuarioParaDto(usuarioAtualizado);
    }

    public UsuarioDTO buscaPorId(Long id) {
        Usuario usuario = IUsuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("usuario.nao.encontrado"));

        return usuarioMapper.usuarioParaDto(usuario);
    }

    public Page<UsuarioDTO> buscaPorFiltro(UsuarioFiltro filtro, Pageable pageable) {
        Page<Usuario> usuarios = IUsuarioRepository.findByNomeContainingAndEmailContaining(
                filtro.getNome() != null ? filtro.getNome() : "",
                filtro.getEmail() != null ? filtro.getEmail() : "",
                pageable
        );

        return usuarios.map(usuarioMapper::usuarioParaDto);
    }

}
