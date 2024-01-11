package com.example.api.integration;

import com.example.api.DTO.ViaCepResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepIntegration {

    @GetMapping("/{cep}/json/")
    ViaCepResponseDto getAddressByCep(@PathVariable("cep") String cep);
}
