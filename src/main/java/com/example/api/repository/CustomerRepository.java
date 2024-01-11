package com.example.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.api.domain.Customer;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	List<Customer> findAllByOrderByNameAsc();
	List<Customer> findByNameContainingIgnoreCase(String name);
	Optional<Customer> findByEmail(String email);
	List<Customer> findByEmailContainingIgnoreCase(String email);
	List<Customer> findByGender(String gender);
	List<Customer> findByAddressesLocalidadeAndAddressesUf(String localidade, String uf);
	List<Customer> findByAddressesLocalidade(String localidade);
	List<Customer> findByAddressesUf(String uf);

	@Query("SELECT c FROM Customer c WHERE " +
			"(:name IS NULL OR c.name LIKE %:name%) AND " +
			"(:email IS NULL OR c.email LIKE %:email%) AND " +
			"(:gender IS NULL OR c.gender = :gender)")
	Page<Customer> findByAttributes(
			@Param("name") String name,
			@Param("email") String email,
			@Param("gender") String gender,
			Pageable pageable
	);

}
