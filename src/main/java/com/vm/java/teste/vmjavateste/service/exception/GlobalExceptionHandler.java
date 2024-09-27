package com.vm.java.teste.vmjavateste.service.exception;

import com.vm.java.teste.vmjavateste.util.MensagemUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException() {
        String mensagemErro = "Ocorreu um erro inesperado";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(mensagemErro));
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioNotFoundException() {
        String mensagemErro =  MensagemUtil.getMessage("usuario.nao.encontrado", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(mensagemErro));
    }

    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioExistenteException() {
        String mensagemErro = MensagemUtil.getMessage("usuario.existente", null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(mensagemErro));
    }

    @ExceptionHandler(SenhaObrigatoriaException.class)
    public ResponseEntity<ErrorResponse> handleSenhaObrigatoriaException() {
        String mensagemErro = MensagemUtil.getMessage("usuario.senha.obrigatoria", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(mensagemErro));
    }

}