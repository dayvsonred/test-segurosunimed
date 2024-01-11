package com.example.api.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

public class CustomAuthorizationFilter extends GenericFilterBean {

    private static final String AUTH_HEADER = "Authorization";
    private static final String ALLOWED_TOKEN = "dayvison";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader(AUTH_HEADER);

        if (token != null && token.equals(ALLOWED_TOKEN)) {
            // Se o token estiver correto, permita o acesso ao end-point
            Authentication authentication = new UsernamePasswordAuthenticationToken("user", null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else {
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
