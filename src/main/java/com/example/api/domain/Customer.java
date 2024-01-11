package com.example.api.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "CUSTOMER")
public class Customer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome não pode estar em branco")
	@Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@NotBlank(message = "O e-mail não pode estar em branco")
	@Email(message = "Formato de e-mail inválido")
	@NotEmpty
	@Email
	private String email;

	@Column(nullable = false)
	@Pattern(regexp = "^[MF]$", message = "Gênero inválido. Use 'M' para masculino, 'F' para feminino.")
	@NotEmpty
	private String gender;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval = true)
	private List<Address> addresses;
}
