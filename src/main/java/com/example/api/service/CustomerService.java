package com.example.api.service;

import java.util.*;
import java.util.stream.Collectors;

import com.example.api.DTO.CustomerDto;
import com.example.api.DTO.CustomerRequestDto;
import com.example.api.DTO.ViaCepResponseDto;
import com.example.api.domain.Address;
import com.example.api.exceptions.CustomerBadRequestException;
import com.example.api.exceptions.CustomerNotFoundException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class CustomerService {

	private CustomerRepository customerRepository;
	private ViaCepService viaCepService;

	@Autowired
	public CustomerService(CustomerRepository customerRepository, ViaCepService viaCepService) {
		this.customerRepository = customerRepository;
		this.viaCepService = viaCepService;
	}

	public List<Customer> findAll() {
		return customerRepository.findAllByOrderByNameAsc();
	}

	public CustomerDto getCustomer(Long id) {
		Customer customer = this.findById(id);
		return CustomerDto.builder()
				.id(customer.getId())
				.name(customer.getName())
				.email(customer.getEmail())
				.addresses(customer.getAddresses())
				.build();

	}

	public Customer findById(Long id) throws CustomerNotFoundException {
			return customerRepository.findById(id).orElseThrow(
					() -> new CustomerNotFoundException("Not Found customer id: " + id)
			);
	}

	public List<Customer> searchByName(String name) {
		List<Customer> customers = customerRepository.findByNameContainingIgnoreCase(name);
		if (customers.isEmpty()) {
			throw new CustomerNotFoundException("Nenhum cliente encontrado com o nome: " + name);
		}
		return customers;
	}

	public List<Customer> searchByEmail(String email) {
		List<Customer> customers = customerRepository.findByEmailContainingIgnoreCase(email);
		if (customers.isEmpty()) {
			throw new CustomerNotFoundException("Nenhum cliente encontrado com o e-mail: " + email);
		}
		return customers;
	}

	public List<Customer> searchByGender(String gender) {
		List<Customer> customers = customerRepository.findByGender(gender);
		if (customers.isEmpty()) {
			throw new CustomerNotFoundException("Nenhum cliente encontrado com o gênero: " + gender);
		}
		return customers;
	}

	public Page<CustomerDto> searchCustomers(String name, String email, String gender, Pageable pageable) {
		return customerRepository.findByAttributes(name, email, gender, pageable)
				.map(CustomerDto::fromEntity);
	}


	public void validExistCustomerWithEmail(String email){
			customerRepository.findByEmail(email).ifPresent( customer -> {
				throw new CustomerBadRequestException("Cliente já cadastrado com email: " + email);
			});
	}

	public CustomerDto createCustomer(CustomerRequestDto customerRequestDto) {
		Customer customer = new Customer(
				null,
				customerRequestDto.getName(),
				customerRequestDto.getEmail(),
				customerRequestDto.getGender(),
				new ArrayList<Address>()
		);
		this.validExistCustomerWithEmail(customerRequestDto.getEmail());

		customer = customerRepository.save(customer);

		addAddressToCustomer(customer, customerRequestDto);

		return CustomerDto.builder()
				.id(customer.getId())
				.name(customer.getName())
				.email(customer.getEmail())
				.gender(customer.getGender())
				.build();
	}

	public CustomerDto updateCustomer(Long id, CustomerDto updatedCustomer) {
		Optional<Customer> existingCustomer = customerRepository.findById(id);

		if (existingCustomer.isPresent()) {
			Customer customerToUpdate = existingCustomer.get();

			customerToUpdate.setName(updatedCustomer.getName());
			customerToUpdate.setGender(updatedCustomer.getGender());

			Customer customer = customerRepository.save(customerToUpdate);
			updatedCustomer.setId(customer.getId());
			updatedCustomer.setEmail(customer.getEmail());
			return updatedCustomer;
		} else {
			throw new CustomerNotFoundException("Cliente não encontrado com o ID: " + id);
		}
	}

	public void deleteCustomer(Long id) {
		Optional<Customer> existingCustomer = customerRepository.findById(id);

		if (existingCustomer.isPresent()) {
			customerRepository.deleteById(id);
		} else {
			throw new CustomerNotFoundException("Cliente não encontrado com o ID: " + id);
		}
	}

	@Async
	private void addAddressToCustomer(Customer customer, CustomerRequestDto customerRequestDto) {
		try {
			//if campo CEP não for vasio
			log.info("Begin addAddressToCustomer customer id: {} and CEP: {}", customer.getId(), customerRequestDto.getCep());
			ViaCepResponseDto viaCepResponseDto = viaCepService.getCep(customerRequestDto.getCep());

			customer.getAddresses().add(Address.builder()
					.cep(viaCepResponseDto.getCep())
					.logradouro(viaCepResponseDto.getLogradouro())
					.complemento(viaCepResponseDto.getComplemento())
					.bairro(viaCepResponseDto.getBairro())
					.localidade(viaCepResponseDto.getLocalidade())
					.uf(viaCepResponseDto.getUf())
					.ibge(viaCepResponseDto.getIbge())
					.gia(viaCepResponseDto.getGia())
					.ddd(fixToIntDDD(viaCepResponseDto.getDdd()))
					.siafi(viaCepResponseDto.getSiafi())
							.customer(Customer.builder().id(customer.getId()).build())
					.build());

			updateCustomerAddress(customer);

			log.info("Finished Success addAddressToCustomer customer id: {} and CEP: {}", customer.getId(), customerRequestDto.getCep());
		}catch (Exception e){
			log.error(e.getMessage());
			throw new RuntimeException("ERROR VIA CEP INTEGRATION customer id: " + customer.getId()
					+ ", CEP: " + customerRequestDto.getCep());
		}
	}

	public void updateCustomerAddress(Customer customer) {
		customerRepository.save(customer);
	}

	public Integer fixToIntDDD(String ddd){
		try {
			return Integer.valueOf(ddd);
		}catch (Exception e){
			log.info("Failed to convert DDD: {}", ddd);
			return 0;
		}
	}

	public Customer addAddressToCustomer(Long customerId, Address address) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Cliente não encontrado com o ID: " + customerId));

		address.setCustomer(customer);
		customer.getAddresses().add(address);

		return customerRepository.save(customer);
	}

	public List<CustomerDto> filterCustomersByLocationAndUf(String localidade, String uf) {
		List<Customer> filteredCustomers;

		if (localidade != null && uf != null) {
			filteredCustomers = customerRepository.findByAddressesLocalidadeAndAddressesUf(localidade, uf);
		} else if (localidade != null) {
			filteredCustomers = customerRepository.findByAddressesLocalidade(localidade);
		} else if (uf != null) {
			filteredCustomers =  customerRepository.findByAddressesUf(uf);
		} else {
			filteredCustomers =  new ArrayList<Customer>();
		}

		return filteredCustomers.stream()
				.map(CustomerDto::fromEntity)
				.collect(Collectors.toList());

	}
}
