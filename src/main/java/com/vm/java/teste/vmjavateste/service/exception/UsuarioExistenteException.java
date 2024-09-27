package com.vm.java.teste.vmjavateste.service.exception;

import com.vm.java.teste.vmjavateste.util.MensagemUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsuarioExistenteException extends ResponseStatusException {
    public UsuarioExistenteException() {
        super(HttpStatus.CONFLICT, MensagemUtil.getMessage("usuario.existente", null));
    }
}