package com.vm.java.teste.vmjavateste.service.exception;

public class ErrorResponse {
    private String mensagem;

    public ErrorResponse(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}