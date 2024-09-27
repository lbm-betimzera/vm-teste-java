package com.vm.java.teste.vmjavateste.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecretKeyFilter extends HttpFilter {
    private final String secretKey;

    public SecretKeyFilter(@Value("${app.security.secret}") String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String providedKey = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/v3/api-docs") ||
                request.getRequestURI().startsWith("/swagger-ui") ||
                request.getRequestURI().startsWith("/swagger-resources") ||
                request.getRequestURI().startsWith("/webjars")) {
            chain.doFilter(request, response);
            return;
        }

        if (providedKey == null || !providedKey.startsWith("Bearer ") || !providedKey.substring(7).equals(secretKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: Invalid Secret Key");
            return;
        }

        chain.doFilter(request, response);
    }
}