package com.vm.java.teste.vmjavateste.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MensagemUtil {

    private static MensagemUtil instance;

    private MessageSource messageSource;

    @Autowired
    public MensagemUtil(MessageSource messageSource) {
        this.messageSource = messageSource;
        instance = this;
    }

    public static String getMessage(String messageKey, Object... args) {
        if (instance == null) {
            throw new IllegalStateException("MessageUtil not initialized");
        }
        return instance.messageSource.getMessage(messageKey, args, Locale.getDefault());
    }
}