package com.example.api.service;

import com.example.api.DTO.ViaCepResponseDto;
import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.integration.ViaCepIntegration;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.FindException;

@Service
@Slf4j
public class ViaCepService {

    private ViaCepIntegration viaCepIntegration;

    @Autowired
    public ViaCepService(ViaCepIntegration viaCepIntegration) {
        this.viaCepIntegration = viaCepIntegration;
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 20000), value = FindException.class)
    public ViaCepResponseDto getCep(String cep){
        try {
            log.info("Call getAddressByCep cep: {}", cep);
            return viaCepIntegration.getAddressByCep(cep);
        } catch (FeignException f) {
            log.error("ERROR  FeignException getCep cep: {}", cep);
            throw new RuntimeException(f.getMessage(), f);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " ERROR IN getCep cep: " + cep);
        }
    }

    @Recover
    public ViaCepResponseDto recoverFromException(Exception ex, String cep) {
        // Lógica para lidar com a falha persistente
        // Pode lançar uma exceção personalizada, retornar um valor padrão, publoicar em um tópico do kafka etc.
        log.info("ERROR in getAddressByCep cep: {}", cep);
        throw new RuntimeException("Falha persistente ao obter o endereço por CEP: " + cep, ex);
    }
}
