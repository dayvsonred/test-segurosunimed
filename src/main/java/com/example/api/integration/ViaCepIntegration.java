package com.example.api.integration;

import com.example.api.DTO.ViaCepResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepIntegration {

    @GetMapping(value = "/{cep}/json/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ViaCepResponseDto getAddressByCep(@PathVariable("cep") String cep);
}
