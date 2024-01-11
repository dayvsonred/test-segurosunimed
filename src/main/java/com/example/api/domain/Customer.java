package com.example.api.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "CUSTOMER")
public class Customer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
