package com.vm.java.teste.vmjavateste.service.exception;

import com.vm.java.teste.vmjavateste.controller.dto.ApiResponse;
import com.vm.java.teste.vmjavateste.util.MensagemUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(UsuarioGenericException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioGenericException() {
        String mensagemErro = MensagemUtil.getMessage("usuario.campo.obrigatorio", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(mensagemErro));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> DataIntegrityViolationException() {
        String mensagemErro = MensagemUtil.getMessage("usuario.campo.obrigatorio", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(null, mensagemErro));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String mensagemErro = MensagemUtil.getMessage("body.ausente", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(null, mensagemErro));
    }

}