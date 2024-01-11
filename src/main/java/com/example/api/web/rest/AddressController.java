package com.example.api.web.rest;

import com.example.api.DTO.AddAddressToCustomerDto;
import com.example.api.DTO.CustomerDto;
import com.example.api.DTO.CustomerRequestDto;
import com.example.api.domain.Customer;
import com.example.api.exceptions.CustomerBadRequestException;
import com.example.api.exceptions.CustomerNotFoundException;
import com.example.api.service.AddressService;
import com.example.api.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/address")
public class AddressController {

	private AddressService addressService;

	@Autowired
	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}

	@PostMapping(value = "/addToCustomer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerDto> findById(@Valid @RequestBody AddAddressToCustomerDto addAddressToCustomerDto) throws CustomerNotFoundException{
		return ResponseEntity.ok(addressService.addAddressToCustomer(addAddressToCustomerDto));
	}
}
