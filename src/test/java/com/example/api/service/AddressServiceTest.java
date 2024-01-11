package com.example.api.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.api.DTO.AddAddressToCustomerDto;
import com.example.api.DTO.CustomerDto;
import com.example.api.DTO.ViaCepResponseDto;
import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.repository.AddressRepository;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AddressService.class})
@ExtendWith(SpringExtension.class)
class AddressServiceTest {
    @MockBean
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private ViaCepService viaCepService;

    /**
     * Method under test: {@link AddressService#addAddressToCustomer(AddAddressToCustomerDto)}
     */
    @Test
    void testAddAddressToCustomer() {
        when(viaCepService.getCep(Mockito.<String>any())).thenReturn(new ViaCepResponseDto("Cep", "Logradouro",
                "alice.liddell@example.org", "Bairro", "Localidade", "Uf", "Ibge", "Gia", "Ddd", "Siafi"));

        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        when(customerService.getCustomer(Mockito.<Long>any()))
                .thenReturn(CustomerDto.builder().email("jane.doe@example.org").gender("Gender").id(1L).name("Name").build());
        when(customerService.addAddressToCustomer(Mockito.<Long>any(), Mockito.<Address>any())).thenReturn(customer);
        when(customerService.fixToIntDDD(Mockito.<String>any())).thenReturn(1);
        addressService.addAddressToCustomer(new AddAddressToCustomerDto());
        verify(viaCepService).getCep(Mockito.<String>any());
        verify(customerService).getCustomer(Mockito.<Long>any());
        verify(customerService).addAddressToCustomer(Mockito.<Long>any(), Mockito.<Address>any());
        verify(customerService).fixToIntDDD(Mockito.<String>any());
    }
}

