package com.vm.java.teste.vmjavateste.service.exception;

import com.vm.java.teste.vmjavateste.util.MensagemUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SenhaObrigatoriaException extends ResponseStatusException {
    public SenhaObrigatoriaException() {
        super(HttpStatus.BAD_REQUEST, MensagemUtil.getMessage("usuario.senha.obrigatoria", null));
    }
}