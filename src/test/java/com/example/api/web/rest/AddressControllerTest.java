package com.example.api.web.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.api.DTO.AddAddressToCustomerDto;
import com.example.api.DTO.CustomerDto;
import com.example.api.DTO.ViaCepResponseDto;
import com.example.api.domain.Customer;
import com.example.api.exceptions.CustomerNotFoundException;
import com.example.api.integration.ViaCepIntegration;
import com.example.api.repository.AddressRepository;
import com.example.api.repository.CustomerRepository;
import com.example.api.service.AddressService;
import com.example.api.service.CustomerService;
import com.example.api.service.ViaCepService;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class AddressControllerTest {
    /**
     * Method under test: {@link AddressController#findById(AddAddressToCustomerDto)}
     */
    @Test
    void testFindById() throws CustomerNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   javax.validation.UnexpectedTypeException: HV000030: No validator could be found for constraint 'javax.validation.constraints.NotEmpty' validating type 'java.lang.Long'. Check configuration for 'customerId'
        //       at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree.getExceptionForNullValidator(ConstraintTree.java:116)
        //       at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree.getInitializedConstraintValidator(ConstraintTree.java:162)
        //       at org.hibernate.validator.internal.engine.constraintvalidation.SimpleConstraintTree.validateConstraints(SimpleConstraintTree.java:54)
        //       at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree.validateConstraints(ConstraintTree.java:75)
        //       at org.hibernate.validator.internal.metadata.core.MetaConstraint.doValidateConstraint(MetaConstraint.java:130)
        //       at org.hibernate.validator.internal.metadata.core.MetaConstraint.validateConstraint(MetaConstraint.java:123)
        //       at org.hibernate.validator.internal.engine.ValidatorImpl.validateMetaConstraint(ValidatorImpl.java:555)
        //       at org.hibernate.validator.internal.engine.ValidatorImpl.validateConstraintsForSingleDefaultGroupElement(ValidatorImpl.java:518)
        //       at org.hibernate.validator.internal.engine.ValidatorImpl.validateConstraintsForDefaultGroup(ValidatorImpl.java:488)
        //       at org.hibernate.validator.internal.engine.ValidatorImpl.validateConstraintsForCurrentGroup(ValidatorImpl.java:450)
        //       at org.hibernate.validator.internal.engine.ValidatorImpl.validateInContext(ValidatorImpl.java:400)
        //       at org.hibernate.validator.internal.engine.ValidatorImpl.validate(ValidatorImpl.java:172)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:681)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //   See https://diff.blue/R013 to resolve this issue.

        ViaCepIntegration viaCepIntegration = mock(ViaCepIntegration.class);
        when(viaCepIntegration.getAddressByCep(Mockito.<String>any())).thenReturn(new ViaCepResponseDto("Cep", "Logradouro",
                "alice.liddell@example.org", "Bairro", "Localidade", "Uf", "Ibge", "Gia", "Ddd", "Siafi"));
        ViaCepService viaCepService = new ViaCepService(viaCepIntegration);

        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);

        Customer customer2 = new Customer();
        customer2.setAddresses(new ArrayList<>());
        customer2.setEmail("jane.doe@example.org");
        customer2.setGender("Gender");
        customer2.setId(1L);
        customer2.setName("Name");
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer2);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        AddressController addressController = new AddressController(new AddressService(mock(AddressRepository.class),
                viaCepService, new CustomerService(customerRepository, new ViaCepService(mock(ViaCepIntegration.class)))));
        ResponseEntity<CustomerDto> actualFindByIdResult = addressController.findById(new AddAddressToCustomerDto());
        assertTrue(actualFindByIdResult.hasBody());
        assertTrue(actualFindByIdResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualFindByIdResult.getStatusCode());
        CustomerDto body = actualFindByIdResult.getBody();
        assertEquals("jane.doe@example.org", body.getEmail());
        assertEquals(1, body.getAddresses().size());
        assertEquals("Name", body.getName());
        assertNull(body.getGender());
        assertEquals(1L, body.getId().longValue());
        verify(viaCepIntegration).getAddressByCep(Mockito.<String>any());
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository, atLeast(1)).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link AddressController#findById(AddAddressToCustomerDto)}
     */
    @Test
    void testFindById2() throws CustomerNotFoundException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   javax.validation.UnexpectedTypeException: HV000030: No validator could be found for constraint 'javax.validation.constraints.NotEmpty' validating type 'java.lang.Long'. Check configuration for 'customerId'
        //       at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree.getExceptionForNullValidator(ConstraintTree.java:116)
        //       at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree.getInitializedConstraintValidator(ConstraintTree.java:162)
        //       at org.hibernate.validator.internal.engine.constraintvalidation.SimpleConstraintTree.validateConstraints(SimpleConstraintTree.java:54)
        //       at org.hibernate.validator.internal.engine.constraintvalidation.ConstraintTree.validateConstraints(ConstraintTree.java:75)
        //       at org.hibernate.validator.internal.metadata.core.MetaConstraint.doValidateConstraint(MetaConstraint.java:130)
        //       at org.hibernate.validator.internal.metadata.core.MetaConstraint.validateConstraint(MetaConstraint.java:123)
        //       at org.hibernate.validator.internal.engine.ValidatorImpl.validateMetaConstraint(ValidatorImpl.java:555)
        //       at org.hibernate.validator.internal.engine.ValidatorImpl.validateConstraintsForSingleDefaultGroupElement(ValidatorImpl.java:518)
        //       at org.hibernate.validator.internal.engine.ValidatorImpl.validateConstraintsForDefaultGroup(ValidatorImpl.java:488)
        //       at org.hibernate.validator.internal.engine.ValidatorImpl.validateConstraintsForCurrentGroup(ValidatorImpl.java:450)
        //       at org.hibernate.validator.internal.engine.ValidatorImpl.validateInContext(ValidatorImpl.java:400)
        //       at org.hibernate.validator.internal.engine.ValidatorImpl.validate(ValidatorImpl.java:172)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:681)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //   See https://diff.blue/R013 to resolve this issue.

        ViaCepIntegration viaCepIntegration = mock(ViaCepIntegration.class);
        when(viaCepIntegration.getAddressByCep(Mockito.<String>any())).thenReturn(new ViaCepResponseDto("Cep",
                "Logradouro", "alice.liddell@example.org", "Bairro", "Localidade", "Uf", "Ibge", "Gia", "42", "Siafi"));
        ViaCepService viaCepService = new ViaCepService(viaCepIntegration);

        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);

        Customer customer2 = new Customer();
        customer2.setAddresses(new ArrayList<>());
        customer2.setEmail("jane.doe@example.org");
        customer2.setGender("Gender");
        customer2.setId(1L);
        customer2.setName("Name");
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer2);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        AddressController addressController = new AddressController(new AddressService(mock(AddressRepository.class),
                viaCepService, new CustomerService(customerRepository, new ViaCepService(mock(ViaCepIntegration.class)))));
        ResponseEntity<CustomerDto> actualFindByIdResult = addressController.findById(new AddAddressToCustomerDto());
        assertTrue(actualFindByIdResult.hasBody());
        assertTrue(actualFindByIdResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualFindByIdResult.getStatusCode());
        CustomerDto body = actualFindByIdResult.getBody();
        assertEquals("jane.doe@example.org", body.getEmail());
        assertEquals(1, body.getAddresses().size());
        assertEquals("Name", body.getName());
        assertNull(body.getGender());
        assertEquals(1L, body.getId().longValue());
        verify(viaCepIntegration).getAddressByCep(Mockito.<String>any());
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository, atLeast(1)).findById(Mockito.<Long>any());
    }
}

