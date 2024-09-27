package com.vm.java.teste.vmjavateste.controller.dto;

import lombok.Data;

@Data
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
}