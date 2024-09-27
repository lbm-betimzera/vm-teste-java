package com.vm.java.teste.vmjavateste.config;


import com.vm.java.teste.vmjavateste.service.enums.TipoEnvioEmail;
import lombok.Data;

@Data
public class EmailConfig {

    private static String NOME_GENERICO = "Email Configuração";
    private String email =  "lucas.marins@teste.com";
    private TipoEnvioEmail tipo;
    private String host;
    private String port;
    private String username;
    private String password;


    public EmailConfig(){
        this.NOME_GENERICO = NOME_GENERICO;
        this.email = email;
    }
}
