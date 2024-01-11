package com.example.api.web.rest;

import java.util.List;

import com.example.api.DTO.CustomerDto;
import com.example.api.DTO.CustomerRequestDto;
import com.example.api.exceptions.CustomerBadRequestException;
import com.example.api.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService customerService;

	@Autowired
	public CustomerController(CustomerService service) {
		this.customerService = service;
	}

	@GetMapping
	public List<Customer> findAll() {
		return customerService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> findById(@PathVariable Long id) throws CustomerNotFoundException{
		return ResponseEntity.ok(customerService.getCustomer(id));
	}

	@GetMapping(value = "/searchByName", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> searchByName(@RequestParam String name) throws CustomerNotFoundException{
			return ResponseEntity.ok(customerService.searchByName(name));
	}

	@GetMapping(value = "/searchByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> searchByEmail(@RequestParam String email)  throws CustomerNotFoundException{
			return ResponseEntity.ok(customerService.searchByEmail(email));
	}

	@GetMapping(value = "/searchByGender", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> searchByGender(@RequestParam String gender) throws CustomerNotFoundException{
			return ResponseEntity.ok(customerService.searchByGender(gender));
	}

	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<CustomerDto>> searchCustomers(
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "gender", required = false) String gender,
			Pageable pageable
	) {
		Page<CustomerDto> customersPage = customerService.searchCustomers(name, email, gender, pageable);
		return new ResponseEntity<>(customersPage, HttpStatus.OK);
	}

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerDto> createCustomer(@RequestBody @Valid CustomerRequestDto customerRequestDto) throws CustomerBadRequestException {
		return new ResponseEntity<>(customerService.createCustomer(customerRequestDto), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<CustomerDto> updateCustomer(
			@PathVariable Long id,
			@RequestBody @Valid CustomerDto customerDto
	) {
		return new ResponseEntity<>(customerService.updateCustomer(id, customerDto), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		customerService.deleteCustomer(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/filterByCiteAndState")
	public ResponseEntity<List<CustomerDto>> filterCustomersByLocationAndUf(
			@RequestParam(required = false) String cidade,
			@RequestParam(required = false) String estado) {
		List<CustomerDto> filteredCustomers = customerService.filterCustomersByLocationAndUf(cidade, estado.toUpperCase());
		return ResponseEntity.ok(filteredCustomers);
	}

}
