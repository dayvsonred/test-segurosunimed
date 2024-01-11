package com.example.api.service;

import com.example.api.DTO.CustomerDto;
import com.example.api.DTO.CustomerRequestDto;
import com.example.api.DTO.ViaCepResponseDto;
import com.example.api.domain.Customer;
import com.example.api.repository.AddressRepository;
import com.example.api.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

//    private ViaCepService viaCepService;
    private AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository
//            , ViaCepService viaCepService
    ) {
        this.addressRepository = addressRepository;
//        this.viaCepService = viaCepService;
    }

//
//    @Async
//    public void addAddressToCustomer(Customer customer, CustomerRequestDto customerRequestDto) {
//        ViaCepResponseDto viaCepResponseDto = viaCepService.getCep(customerRequestDto.getCep());
//
//
//
//
//    }
}
