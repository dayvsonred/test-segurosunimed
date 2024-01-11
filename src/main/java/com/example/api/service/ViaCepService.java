package com.example.api.service;

import com.example.api.DTO.ViaCepResponseDto;
import com.example.api.integration.ViaCepIntegration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.retry.annotation.Backoff;
//import org.springframework.retry.annotation.Recover;
//import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ViaCepService {

//    private ViaCepIntegration viaCepIntegration;
//
//    @Autowired
//    public ViaCepService(ViaCepIntegration viaCepIntegration) {
//        this.viaCepIntegration = viaCepIntegration;
//    }
//
////    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 20000))
//    public ViaCepResponseDto getCep(String cep){
//        log.info("Call getAddressByCep cep: {}", cep);
//        return viaCepIntegration.getAddressByCep(cep);
//    }

//    @Recover
//    public ViaCepResponseDto recoverFromException(Exception ex, String cep) {
//        // Lógica para lidar com a falha persistente
//        // Pode lançar uma exceção personalizada, retornar um valor padrão, publoicar em um tópico do kafka etc.
//        log.info("ERROR in getAddressByCep cep: {}", cep);
//        throw new RuntimeException("Falha persistente ao obter o endereço por CEP: " + cep, ex);
//    }
}
