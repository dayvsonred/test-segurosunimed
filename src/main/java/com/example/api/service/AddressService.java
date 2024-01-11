package com.example.api.service;

import com.example.api.DTO.AddAddressToCustomerDto;
import com.example.api.DTO.CustomerDto;
import com.example.api.DTO.CustomerRequestDto;
import com.example.api.DTO.ViaCepResponseDto;
import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.repository.AddressRepository;
import com.example.api.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AddressService {

    private ViaCepService viaCepService;
    private AddressRepository addressRepository;
    private CustomerService customerService;

    @Autowired
    public AddressService(AddressRepository addressRepository, ViaCepService viaCepService, CustomerService customerService) {
        this.addressRepository = addressRepository;
        this.viaCepService = viaCepService;
        this.customerService = customerService;
    }

    public CustomerDto addAddressToCustomer(AddAddressToCustomerDto addAddressToCustomerDto){
        log.info("Begin addAddressToCustomer customer id: {} and CEP: {}", addAddressToCustomerDto.getCustomerId(), addAddressToCustomerDto.getCep());
        ViaCepResponseDto viaCepResponseDto = viaCepService.getCep(addAddressToCustomerDto.getCep());

        customerService.addAddressToCustomer(addAddressToCustomerDto.getCustomerId(), Address.builder()
                .cep(viaCepResponseDto.getCep())
                .logradouro(viaCepResponseDto.getLogradouro())
                .complemento(viaCepResponseDto.getComplemento())
                .bairro(viaCepResponseDto.getBairro())
                .localidade(viaCepResponseDto.getLocalidade())
                .uf(viaCepResponseDto.getUf())
                .ibge(viaCepResponseDto.getIbge())
                .gia(viaCepResponseDto.getGia())
                .ddd(customerService.fixToIntDDD(viaCepResponseDto.getDdd()))
                .siafi(viaCepResponseDto.getSiafi())
                .customer(Customer.builder().id(addAddressToCustomerDto.getCustomerId()).build())
                .build());

        return customerService.getCustomer(addAddressToCustomerDto.getCustomerId());
    }
}
