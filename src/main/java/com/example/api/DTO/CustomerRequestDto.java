package com.example.api.DTO;

import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequestDto implements Serializable {

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String name;

    @NotBlank(message = "O e-mail não pode estar em branco")
    @Email(message = "Formato de e-mail inválido")
    @NotEmpty
    private String email;

    @Pattern(regexp = "^[MF]$", message = "Gênero inválido. Use 'M' para masculino, 'F' para feminino.")
    @NotEmpty
    private String gender;

    @NotBlank(message = "O CEP não pode estar em branco")
    @Size(min = 8, max = 8, message = "O CEP deve ter 8 caracteres")
    @Pattern(regexp = "\\d{8}", message = "Formato de CEP inválido")
    private String cep;
}
