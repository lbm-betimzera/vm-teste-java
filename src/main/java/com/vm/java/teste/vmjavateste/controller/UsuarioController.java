package com.vm.java.teste.vmjavateste.controller;

import com.vm.java.teste.vmjavateste.controller.dto.ApiResponse;
import com.vm.java.teste.vmjavateste.controller.dto.UsuarioFiltro;
import com.vm.java.teste.vmjavateste.service.UsuarioService;
import com.vm.java.teste.vmjavateste.controller.dto.UsuarioDTO;
import com.vm.java.teste.vmjavateste.service.exception.UsuarioNotFoundException;
import com.vm.java.teste.vmjavateste.util.MensagemUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/cria-usuario")
    public ResponseEntity<ApiResponse<String>> criaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            usuarioService.criaUsuario(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(null, MensagemUtil.getMessage("usuario.criado.sucesso")));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, e.getMessage()));
        } catch (Exception e) {
            String reason = ((ResponseStatusException) e).getReason();
            HttpStatusCode statusCode = ((ResponseStatusException) e).getStatusCode();
            return ResponseEntity.status(statusCode).body(new ApiResponse<>(null, reason));
        }
    }

    @PutMapping("/edita-usuario/{id}")
    public ResponseEntity<ApiResponse<String>> editaUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            usuarioService.editaUsuario(id, usuarioDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(null, MensagemUtil.getMessage("usuario.editado.sucesso")));
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, e.getMessage()));
        } catch (Exception e) {
            String reason = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, reason));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> buscaPorId(@PathVariable Long id) {
        try {
            UsuarioDTO usuario = usuarioService.buscaPorId(id);
            return ResponseEntity.ok(new ApiResponse<>(usuario));
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, e.getMessage()));
        } catch (Exception e) {
            String reason = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(null, reason));
        }
    }

    @GetMapping("/busca-paginada")
    public ResponseEntity<ApiResponse<Page<UsuarioDTO>>> buscaPorFiltro(@RequestBody UsuarioFiltro filtro, @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<UsuarioDTO> usuariosDTO = usuarioService.buscaPorFiltro(filtro, pageable);
            return ResponseEntity.ok(new ApiResponse<>(usuariosDTO));
        } catch (Exception e) {
            String reason = ((ResponseStatusException) e).getReason();
            HttpStatusCode statusCode = ((ResponseStatusException) e).getStatusCode();
            return ResponseEntity.status(statusCode).body(new ApiResponse<>(null, reason));
        }
    }
}