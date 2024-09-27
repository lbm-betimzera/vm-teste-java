package com.vm.java.teste.vmjavateste.service;

import com.vm.java.teste.vmjavateste.config.EmailConfig;
import com.vm.java.teste.vmjavateste.service.enums.TipoEnvioEmail;
import com.vm.java.teste.vmjavateste.util.MensagemUtil;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviaEmail(String para, String nome, TipoEnvioEmail tipoEnvioEmail) {
        EmailConfig config = new EmailConfig();
        config.setTipo(tipoEnvioEmail);

        switch (tipoEnvioEmail) {
            case CRIACAO -> enviaEmailCriacaoConta(config, para, nome);
            case ALTERACAO -> enviaEmailAlteracao(config, para, nome);
        }

        System.out.println("Email enviado com sucesso para: " + para + " Tipo: " +  " " + tipoEnvioEmail);
    }


    public void enviaEmailCriacaoConta(EmailConfig config, String para, String nome) {
        String de = config.getEmail();
        System.out.println("Config email enviando de: " + de);
        System.out.println("Email enviado com sucesso para: " + para);
        System.out.println("Assunto: " +  MensagemUtil.getMessage("email.assunto.CRIACAO",null));
        System.out.println("corpo: " +  MensagemUtil.getMessage("email.padrao", nome, "testesenha"));
    }

    public void enviaEmailAlteracao(EmailConfig config, String para, String nome) {
        String de = config.getEmail();
        TipoEnvioEmail assunto = config.getTipo();
        System.out.println("Config email enviando de: " + de);
        System.out.println("Email enviado com sucesso para: " + para);
        System.out.println("Assunto: " +  assunto + " " + MensagemUtil.getMessage("email.assunto.ALTERACAO",null));
        System.out.println("corpo: " +  MensagemUtil.getMessage("email.alteracao", nome));
    }

}
