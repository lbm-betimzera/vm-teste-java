package com.vm.java.teste.vmjavateste.service.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class UsuarioNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private static MessageSource messageSource;

    @Autowired
    public UsuarioNotFoundException(MessageSource messageSource) {
        UsuarioNotFoundException.messageSource = messageSource;
    }

    public UsuarioNotFoundException(String messageKey) {
        super(getMessage(messageKey));
    }

    private static String getMessage(String messageKey) {
        if (messageSource == null) {
            return messageKey;
        }
        return messageSource.getMessage(messageKey, null, null);
    }
}