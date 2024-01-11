package com.example.api.DTO;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddAddressToCustomerDto implements Serializable {

    @NotBlank
    @NotEmpty
    private Long customerId;
    @NotBlank(message = "O CEP não pode estar em branco")
    @Size(min = 8, max = 8, message = "O CEP deve ter 8 caracteres")
    @Pattern(regexp = "\\d{8}", message = "Formato de CEP inválido")
    private String cep;

}
