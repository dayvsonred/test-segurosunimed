package com.example.api.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.api.DTO.ViaCepResponseDto;
import com.example.api.integration.ViaCepIntegration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ContextConfiguration(classes = {ViaCepService.class})
@ExtendWith(SpringExtension.class)
class ViaCepServiceTest {
    @MockBean
    private ViaCepIntegration viaCepIntegration;

    @Autowired
    private ViaCepService viaCepService;

    /**
     * Method under test: {@link ViaCepService#getCep(String)}
     */
    @Test
    void testGetCep() {
        ViaCepResponseDto viaCepResponseDto = new ViaCepResponseDto("Cep", "Logradouro", "alice.liddell@example.org",
                "Bairro", "Localidade", "Uf", "Ibge", "Gia", "Ddd", "Siafi");

        when(viaCepIntegration.getAddressByCep(Mockito.<String>any())).thenReturn(viaCepResponseDto);
        assertSame(viaCepResponseDto, viaCepService.getCep("Cep"));
        verify(viaCepIntegration).getAddressByCep(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ViaCepService#getCep(String)}
     */
    @Test
    void testGetCep2() {
        when(viaCepIntegration.getAddressByCep(Mockito.<String>any()))
                .thenThrow(new RuntimeException("Call getAddressByCep cep: {}"));
        assertThrows(ResponseStatusException.class, () -> viaCepService.getCep("Cep"));
        verify(viaCepIntegration).getAddressByCep(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ViaCepService#recoverFromException(Exception, String)}
     */
    @Test
    void testRecoverFromException() {
        assertThrows(RuntimeException.class, () -> viaCepService.recoverFromException(new Exception("foo"), "Cep"));
    }
}

