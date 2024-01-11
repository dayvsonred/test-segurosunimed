package com.example.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.api.DTO.CustomerDto;
import com.example.api.DTO.CustomerRequestDto;
import com.example.api.DTO.ViaCepResponseDto;
import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.exceptions.CustomerBadRequestException;
import com.example.api.exceptions.CustomerNotFoundException;
import com.example.api.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerService.class})
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @MockBean
    private ViaCepService viaCepService;

    /**
     * Method under test: {@link CustomerService#findAll()}
     */
    @Test
    void testFindAll() {
        ArrayList<Customer> customerList = new ArrayList<>();
        when(customerRepository.findAllByOrderByNameAsc()).thenReturn(customerList);
        List<Customer> actualFindAllResult = customerService.findAll();
        assertSame(customerList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(customerRepository).findAllByOrderByNameAsc();
    }

    /**
     * Method under test: {@link CustomerService#findAll()}
     */
    @Test
    void testFindAll2() {
        when(customerRepository.findAllByOrderByNameAsc()).thenThrow(new CustomerNotFoundException("An error occurred"));
        assertThrows(CustomerNotFoundException.class, () -> customerService.findAll());
        verify(customerRepository).findAllByOrderByNameAsc();
    }

    /**
     * Method under test: {@link CustomerService#getCustomer(Long)}
     */
    @Test
    void testGetCustomer() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        CustomerDto actualCustomer = customerService.getCustomer(1L);
        assertTrue(actualCustomer.getAddresses().isEmpty());
        assertEquals("Name", actualCustomer.getName());
        assertEquals(1L, actualCustomer.getId().longValue());
        assertNull(actualCustomer.getGender());
        assertEquals("jane.doe@example.org", actualCustomer.getEmail());
        verify(customerRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomer(Long)}
     */
    @Test
    void testGetCustomer2() {
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(1L);
        when(customer.getEmail()).thenReturn("jane.doe@example.org");
        when(customer.getName()).thenReturn("Name");
        when(customer.getAddresses()).thenReturn(new ArrayList<>());
        doNothing().when(customer).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer).setEmail(Mockito.<String>any());
        doNothing().when(customer).setGender(Mockito.<String>any());
        doNothing().when(customer).setId(Mockito.<Long>any());
        doNothing().when(customer).setName(Mockito.<String>any());
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        CustomerDto actualCustomer = customerService.getCustomer(1L);
        assertTrue(actualCustomer.getAddresses().isEmpty());
        assertEquals("Name", actualCustomer.getName());
        assertEquals(1L, actualCustomer.getId().longValue());
        assertNull(actualCustomer.getGender());
        assertEquals("jane.doe@example.org", actualCustomer.getEmail());
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(customer).getId();
        verify(customer).getEmail();
        verify(customer).getName();
        verify(customer).getAddresses();
        verify(customer).setAddresses(Mockito.<List<Address>>any());
        verify(customer).setEmail(Mockito.<String>any());
        verify(customer).setGender(Mockito.<String>any());
        verify(customer).setId(Mockito.<Long>any());
        verify(customer).setName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#getCustomer(Long)}
     */
    @Test
    void testGetCustomer3() {
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(1L));
        verify(customerRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CustomerService#findById(Long)}
     */
    @Test
    void testFindById() throws CustomerNotFoundException {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(customer, customerService.findById(1L));
        verify(customerRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CustomerService#findById(Long)}
     */
    @Test
    void testFindById2() throws CustomerNotFoundException {
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(1L));
        verify(customerRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CustomerService#findById(Long)}
     */
    @Test
    void testFindById3() throws CustomerNotFoundException {
        when(customerRepository.findById(Mockito.<Long>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(1L));
        verify(customerRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CustomerService#searchByName(String)}
     */
    @Test
    void testSearchByName() {
        when(customerRepository.findByNameContainingIgnoreCase(Mockito.<String>any())).thenReturn(new ArrayList<>());
        assertThrows(CustomerNotFoundException.class, () -> customerService.searchByName("Name"));
        verify(customerRepository).findByNameContainingIgnoreCase(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#searchByName(String)}
     */
    @Test
    void testSearchByName2() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findByNameContainingIgnoreCase(Mockito.<String>any())).thenReturn(customerList);
        List<Customer> actualSearchByNameResult = customerService.searchByName("Name");
        assertSame(customerList, actualSearchByNameResult);
        assertEquals(1, actualSearchByNameResult.size());
        verify(customerRepository).findByNameContainingIgnoreCase(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#searchByName(String)}
     */
    @Test
    void testSearchByName3() {
        when(customerRepository.findByNameContainingIgnoreCase(Mockito.<String>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        assertThrows(CustomerNotFoundException.class, () -> customerService.searchByName("Name"));
        verify(customerRepository).findByNameContainingIgnoreCase(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#searchByEmail(String)}
     */
    @Test
    void testSearchByEmail() {
        when(customerRepository.findByEmailContainingIgnoreCase(Mockito.<String>any())).thenReturn(new ArrayList<>());
        assertThrows(CustomerNotFoundException.class, () -> customerService.searchByEmail("jane.doe@example.org"));
        verify(customerRepository).findByEmailContainingIgnoreCase(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#searchByEmail(String)}
     */
    @Test
    void testSearchByEmail2() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findByEmailContainingIgnoreCase(Mockito.<String>any())).thenReturn(customerList);
        List<Customer> actualSearchByEmailResult = customerService.searchByEmail("jane.doe@example.org");
        assertSame(customerList, actualSearchByEmailResult);
        assertEquals(1, actualSearchByEmailResult.size());
        verify(customerRepository).findByEmailContainingIgnoreCase(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#searchByEmail(String)}
     */
    @Test
    void testSearchByEmail3() {
        when(customerRepository.findByEmailContainingIgnoreCase(Mockito.<String>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        assertThrows(CustomerNotFoundException.class, () -> customerService.searchByEmail("jane.doe@example.org"));
        verify(customerRepository).findByEmailContainingIgnoreCase(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#searchByGender(String)}
     */
    @Test
    void testSearchByGender() {
        when(customerRepository.findByGender(Mockito.<String>any())).thenReturn(new ArrayList<>());
        assertThrows(CustomerNotFoundException.class, () -> customerService.searchByGender("Gender"));
        verify(customerRepository).findByGender(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#searchByGender(String)}
     */
    @Test
    void testSearchByGender2() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findByGender(Mockito.<String>any())).thenReturn(customerList);
        List<Customer> actualSearchByGenderResult = customerService.searchByGender("Gender");
        assertSame(customerList, actualSearchByGenderResult);
        assertEquals(1, actualSearchByGenderResult.size());
        verify(customerRepository).findByGender(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#searchByGender(String)}
     */
    @Test
    void testSearchByGender3() {
        when(customerRepository.findByGender(Mockito.<String>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        assertThrows(CustomerNotFoundException.class, () -> customerService.searchByGender("Gender"));
        verify(customerRepository).findByGender(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#searchCustomers(String, String, String, Pageable)}
     */
    @Test
    void testSearchCustomers() {
        when(customerRepository.findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        assertTrue(customerService.searchCustomers("Name", "jane.doe@example.org", "Gender", null).toList().isEmpty());
        verify(customerRepository).findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link CustomerService#searchCustomers(String, String, String, Pageable)}
     */
    @Test
    void testSearchCustomers2() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");

        ArrayList<Customer> content = new ArrayList<>();
        content.add(customer);
        PageImpl<Customer> pageImpl = new PageImpl<>(content);
        when(customerRepository.findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any())).thenReturn(pageImpl);
        List<CustomerDto> toListResult = customerService.searchCustomers("Name", "jane.doe@example.org", "Gender", null)
                .toList();
        assertEquals(1, toListResult.size());
        CustomerDto getResult = toListResult.get(0);
        assertTrue(getResult.getAddresses().isEmpty());
        assertEquals("Name", getResult.getName());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals("Gender", getResult.getGender());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        verify(customerRepository).findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link CustomerService#searchCustomers(String, String, String, Pageable)}
     */
    @Test
    void testSearchCustomers3() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");

        Customer customer2 = new Customer();
        ArrayList<Address> addresses = new ArrayList<>();
        customer2.setAddresses(addresses);
        customer2.setEmail("john.smith@example.org");
        customer2.setGender("42");
        customer2.setId(2L);
        customer2.setName("42");

        ArrayList<Customer> content = new ArrayList<>();
        content.add(customer2);
        content.add(customer);
        PageImpl<Customer> pageImpl = new PageImpl<>(content);
        when(customerRepository.findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any())).thenReturn(pageImpl);
        List<CustomerDto> toListResult = customerService.searchCustomers("Name", "jane.doe@example.org", "Gender", null)
                .toList();
        assertEquals(2, toListResult.size());
        CustomerDto getResult = toListResult.get(0);
        assertEquals("42", getResult.getName());
        CustomerDto getResult2 = toListResult.get(1);
        assertEquals("Name", getResult2.getName());
        assertEquals(1L, getResult2.getId().longValue());
        assertEquals("Gender", getResult2.getGender());
        assertEquals("jane.doe@example.org", getResult2.getEmail());
        assertEquals(addresses, getResult2.getAddresses());
        assertEquals(2L, getResult.getId().longValue());
        assertEquals("42", getResult.getGender());
        assertEquals("john.smith@example.org", getResult.getEmail());
        assertTrue(getResult.getAddresses().isEmpty());
        verify(customerRepository).findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link CustomerService#searchCustomers(String, String, String, Pageable)}
     */
    @Test
    void testSearchCustomers4() {
        when(customerRepository.findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any())).thenThrow(new CustomerNotFoundException("An error occurred"));
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.searchCustomers("Name", "jane.doe@example.org", "Gender", null));
        verify(customerRepository).findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link CustomerService#searchCustomers(String, String, String, Pageable)}
     */
    @Test
    void testSearchCustomers5() {
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(1L);
        when(customer.getEmail()).thenReturn("jane.doe@example.org");
        when(customer.getGender()).thenReturn("Gender");
        when(customer.getName()).thenReturn("Name");
        when(customer.getAddresses()).thenReturn(new ArrayList<>());
        doNothing().when(customer).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer).setEmail(Mockito.<String>any());
        doNothing().when(customer).setGender(Mockito.<String>any());
        doNothing().when(customer).setId(Mockito.<Long>any());
        doNothing().when(customer).setName(Mockito.<String>any());
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");

        ArrayList<Customer> content = new ArrayList<>();
        content.add(customer);
        PageImpl<Customer> pageImpl = new PageImpl<>(content);
        when(customerRepository.findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any())).thenReturn(pageImpl);
        List<CustomerDto> toListResult = customerService.searchCustomers("Name", "jane.doe@example.org", "Gender", null)
                .toList();
        assertEquals(1, toListResult.size());
        CustomerDto getResult = toListResult.get(0);
        assertTrue(getResult.getAddresses().isEmpty());
        assertEquals("Name", getResult.getName());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals("Gender", getResult.getGender());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        verify(customerRepository).findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any());
        verify(customer).getId();
        verify(customer).getEmail();
        verify(customer).getGender();
        verify(customer).getName();
        verify(customer).getAddresses();
        verify(customer).setAddresses(Mockito.<List<Address>>any());
        verify(customer).setEmail(Mockito.<String>any());
        verify(customer).setGender(Mockito.<String>any());
        verify(customer).setId(Mockito.<Long>any());
        verify(customer).setName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#validExistCustomerWithEmail(String)}
     */
    @Test
    void testValidExistCustomerWithEmail() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(CustomerBadRequestException.class,
                () -> customerService.validExistCustomerWithEmail("jane.doe@example.org"));
        verify(customerRepository).findByEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#validExistCustomerWithEmail(String)}
     */
    @Test
    void testValidExistCustomerWithEmail2() {
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
        customerService.validExistCustomerWithEmail("jane.doe@example.org");
        verify(customerRepository).findByEmail(Mockito.<String>any());
        assertTrue(customerService.findAll().isEmpty());
    }

    /**
     * Method under test: {@link CustomerService#validExistCustomerWithEmail(String)}
     */
    @Test
    void testValidExistCustomerWithEmail3() {
        when(customerRepository.findByEmail(Mockito.<String>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.validExistCustomerWithEmail("jane.doe@example.org"));
        verify(customerRepository).findByEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#createCustomer(CustomerRequestDto)}
     */
    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(CustomerBadRequestException.class, () -> customerService
                .createCustomer(new CustomerRequestDto("Name", "jane.doe@example.org", "Gender", "Cep")));
        verify(customerRepository).findByEmail(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#createCustomer(CustomerRequestDto)}
     */
    @Test
    void testCreateCustomer2() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer);
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
        when(viaCepService.getCep(Mockito.<String>any())).thenReturn(new ViaCepResponseDto("Cep", "Logradouro",
                "alice.liddell@example.org", "Bairro", "Localidade", "Uf", "Ibge", "Gia", "Ddd", "Siafi"));
        CustomerDto actualCreateCustomerResult = customerService
                .createCustomer(new CustomerRequestDto("Name", "jane.doe@example.org", "Gender", "Cep"));
        assertNull(actualCreateCustomerResult.getAddresses());
        assertEquals("Name", actualCreateCustomerResult.getName());
        assertEquals(1L, actualCreateCustomerResult.getId().longValue());
        assertEquals("Gender", actualCreateCustomerResult.getGender());
        assertEquals("jane.doe@example.org", actualCreateCustomerResult.getEmail());
        verify(customerRepository, atLeast(1)).save(Mockito.<Customer>any());
        verify(customerRepository).findByEmail(Mockito.<String>any());
        verify(viaCepService).getCep(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#createCustomer(CustomerRequestDto)}
     */
    @Test
    void testCreateCustomer3() {
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(1L);
        when(customer.getEmail()).thenReturn("jane.doe@example.org");
        when(customer.getGender()).thenReturn("Gender");
        when(customer.getName()).thenReturn("Name");
        when(customer.getAddresses()).thenReturn(new ArrayList<>());
        doNothing().when(customer).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer).setEmail(Mockito.<String>any());
        doNothing().when(customer).setGender(Mockito.<String>any());
        doNothing().when(customer).setId(Mockito.<Long>any());
        doNothing().when(customer).setName(Mockito.<String>any());
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer);
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
        when(viaCepService.getCep(Mockito.<String>any())).thenReturn(new ViaCepResponseDto("Cep", "Logradouro",
                "alice.liddell@example.org", "Bairro", "Localidade", "Uf", "Ibge", "Gia", "Ddd", "Siafi"));
        CustomerDto actualCreateCustomerResult = customerService
                .createCustomer(new CustomerRequestDto("Name", "jane.doe@example.org", "Gender", "Cep"));
        assertNull(actualCreateCustomerResult.getAddresses());
        assertEquals("Name", actualCreateCustomerResult.getName());
        assertEquals(1L, actualCreateCustomerResult.getId().longValue());
        assertEquals("Gender", actualCreateCustomerResult.getGender());
        assertEquals("jane.doe@example.org", actualCreateCustomerResult.getEmail());
        verify(customerRepository, atLeast(1)).save(Mockito.<Customer>any());
        verify(customerRepository).findByEmail(Mockito.<String>any());
        verify(customer, atLeast(1)).getId();
        verify(customer).getEmail();
        verify(customer).getGender();
        verify(customer).getName();
        verify(customer).getAddresses();
        verify(customer).setAddresses(Mockito.<List<Address>>any());
        verify(customer).setEmail(Mockito.<String>any());
        verify(customer).setGender(Mockito.<String>any());
        verify(customer).setId(Mockito.<Long>any());
        verify(customer).setName(Mockito.<String>any());
        verify(viaCepService).getCep(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#createCustomer(CustomerRequestDto)}
     */
    @Test
    void testCreateCustomer4() {
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(1L);
        when(customer.getEmail()).thenReturn("jane.doe@example.org");
        when(customer.getGender()).thenReturn("Gender");
        when(customer.getName()).thenReturn("Name");
        when(customer.getAddresses()).thenReturn(new ArrayList<>());
        doNothing().when(customer).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer).setEmail(Mockito.<String>any());
        doNothing().when(customer).setGender(Mockito.<String>any());
        doNothing().when(customer).setId(Mockito.<Long>any());
        doNothing().when(customer).setName(Mockito.<String>any());
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer);
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
        when(viaCepService.getCep(Mockito.<String>any())).thenReturn(new ViaCepResponseDto("Cep", "Logradouro",
                "alice.liddell@example.org", "Bairro", "Localidade", "Uf", "Ibge", "Gia", "42", "Siafi"));
        CustomerDto actualCreateCustomerResult = customerService
                .createCustomer(new CustomerRequestDto("Name", "jane.doe@example.org", "Gender", "Cep"));
        assertNull(actualCreateCustomerResult.getAddresses());
        assertEquals("Name", actualCreateCustomerResult.getName());
        assertEquals(1L, actualCreateCustomerResult.getId().longValue());
        assertEquals("Gender", actualCreateCustomerResult.getGender());
        assertEquals("jane.doe@example.org", actualCreateCustomerResult.getEmail());
        verify(customerRepository, atLeast(1)).save(Mockito.<Customer>any());
        verify(customerRepository).findByEmail(Mockito.<String>any());
        verify(customer, atLeast(1)).getId();
        verify(customer).getEmail();
        verify(customer).getGender();
        verify(customer).getName();
        verify(customer).getAddresses();
        verify(customer).setAddresses(Mockito.<List<Address>>any());
        verify(customer).setEmail(Mockito.<String>any());
        verify(customer).setGender(Mockito.<String>any());
        verify(customer).setId(Mockito.<Long>any());
        verify(customer).setName(Mockito.<String>any());
        verify(viaCepService).getCep(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#createCustomer(CustomerRequestDto)}
     */
    @Test
    void testCreateCustomer5() {
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(1L);
        when(customer.getAddresses()).thenReturn(new ArrayList<>());
        doNothing().when(customer).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer).setEmail(Mockito.<String>any());
        doNothing().when(customer).setGender(Mockito.<String>any());
        doNothing().when(customer).setId(Mockito.<Long>any());
        doNothing().when(customer).setName(Mockito.<String>any());
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer);
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
        when(viaCepService.getCep(Mockito.<String>any())).thenReturn(null);
        assertThrows(RuntimeException.class, () -> customerService
                .createCustomer(new CustomerRequestDto("Name", "jane.doe@example.org", "Gender", "Cep")));
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository).findByEmail(Mockito.<String>any());
        verify(customer, atLeast(1)).getId();
        verify(customer).getAddresses();
        verify(customer).setAddresses(Mockito.<List<Address>>any());
        verify(customer).setEmail(Mockito.<String>any());
        verify(customer).setGender(Mockito.<String>any());
        verify(customer).setId(Mockito.<Long>any());
        verify(customer).setName(Mockito.<String>any());
        verify(viaCepService).getCep(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#createCustomer(CustomerRequestDto)}
     */
    @Test
    void testCreateCustomer6() {
        Customer customer = mock(Customer.class);
        when(customer.getId()).thenReturn(1L);
        when(customer.getAddresses()).thenReturn(new ArrayList<>());
        doNothing().when(customer).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer).setEmail(Mockito.<String>any());
        doNothing().when(customer).setGender(Mockito.<String>any());
        doNothing().when(customer).setId(Mockito.<Long>any());
        doNothing().when(customer).setName(Mockito.<String>any());
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer);
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
        ViaCepResponseDto viaCepResponseDto = mock(ViaCepResponseDto.class);
        when(viaCepResponseDto.getCep()).thenThrow(new CustomerBadRequestException("An error occurred"));
        when(viaCepService.getCep(Mockito.<String>any())).thenReturn(viaCepResponseDto);
        assertThrows(RuntimeException.class, () -> customerService
                .createCustomer(new CustomerRequestDto("Name", "jane.doe@example.org", "Gender", "Cep")));
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository).findByEmail(Mockito.<String>any());
        verify(customer, atLeast(1)).getId();
        verify(customer).getAddresses();
        verify(customer).setAddresses(Mockito.<List<Address>>any());
        verify(customer).setEmail(Mockito.<String>any());
        verify(customer).setGender(Mockito.<String>any());
        verify(customer).setId(Mockito.<Long>any());
        verify(customer).setName(Mockito.<String>any());
        verify(viaCepService).getCep(Mockito.<String>any());
        verify(viaCepResponseDto).getCep();
    }

    /**
     * Method under test: {@link CustomerService#updateCustomer(Long, CustomerDto)}
     */
    @Test
    void testUpdateCustomer() {
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
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer2);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        CustomerDto updatedCustomer = new CustomerDto();
        CustomerDto actualUpdateCustomerResult = customerService.updateCustomer(1L, updatedCustomer);
        assertSame(updatedCustomer, actualUpdateCustomerResult);
        assertEquals(1L, actualUpdateCustomerResult.getId().longValue());
        assertEquals("jane.doe@example.org", actualUpdateCustomerResult.getEmail());
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CustomerService#updateCustomer(Long, CustomerDto)}
     */
    @Test
    void testUpdateCustomer2() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.save(Mockito.<Customer>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(1L, new CustomerDto()));
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CustomerService#updateCustomer(Long, CustomerDto)}
     */
    @Test
    void testUpdateCustomer3() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        Customer customer2 = mock(Customer.class);
        when(customer2.getId()).thenReturn(1L);
        when(customer2.getEmail()).thenReturn("jane.doe@example.org");
        doNothing().when(customer2).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer2).setEmail(Mockito.<String>any());
        doNothing().when(customer2).setGender(Mockito.<String>any());
        doNothing().when(customer2).setId(Mockito.<Long>any());
        doNothing().when(customer2).setName(Mockito.<String>any());
        customer2.setAddresses(new ArrayList<>());
        customer2.setEmail("jane.doe@example.org");
        customer2.setGender("Gender");
        customer2.setId(1L);
        customer2.setName("Name");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer2);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        CustomerDto updatedCustomer = new CustomerDto();
        CustomerDto actualUpdateCustomerResult = customerService.updateCustomer(1L, updatedCustomer);
        assertSame(updatedCustomer, actualUpdateCustomerResult);
        assertEquals(1L, actualUpdateCustomerResult.getId().longValue());
        assertEquals("jane.doe@example.org", actualUpdateCustomerResult.getEmail());
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(customer2).getId();
        verify(customer2).getEmail();
        verify(customer2).setAddresses(Mockito.<List<Address>>any());
        verify(customer2).setEmail(Mockito.<String>any());
        verify(customer2).setGender(Mockito.<String>any());
        verify(customer2).setId(Mockito.<Long>any());
        verify(customer2).setName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#updateCustomer(Long, CustomerDto)}
     */
    @Test
    void testUpdateCustomer4() {
        Customer customer = mock(Customer.class);
        doNothing().when(customer).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer).setEmail(Mockito.<String>any());
        doNothing().when(customer).setGender(Mockito.<String>any());
        doNothing().when(customer).setId(Mockito.<Long>any());
        doNothing().when(customer).setName(Mockito.<String>any());
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        Customer customer2 = mock(Customer.class);
        when(customer2.getId()).thenReturn(1L);
        when(customer2.getEmail()).thenReturn("jane.doe@example.org");
        doNothing().when(customer2).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer2).setEmail(Mockito.<String>any());
        doNothing().when(customer2).setGender(Mockito.<String>any());
        doNothing().when(customer2).setId(Mockito.<Long>any());
        doNothing().when(customer2).setName(Mockito.<String>any());
        customer2.setAddresses(new ArrayList<>());
        customer2.setEmail("jane.doe@example.org");
        customer2.setGender("Gender");
        customer2.setId(1L);
        customer2.setName("Name");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer2);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        CustomerDto updatedCustomer = new CustomerDto();
        CustomerDto actualUpdateCustomerResult = customerService.updateCustomer(1L, updatedCustomer);
        assertSame(updatedCustomer, actualUpdateCustomerResult);
        assertEquals(1L, actualUpdateCustomerResult.getId().longValue());
        assertEquals("jane.doe@example.org", actualUpdateCustomerResult.getEmail());
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(customer2).getId();
        verify(customer2).getEmail();
        verify(customer2).setAddresses(Mockito.<List<Address>>any());
        verify(customer2).setEmail(Mockito.<String>any());
        verify(customer2).setGender(Mockito.<String>any());
        verify(customer2).setId(Mockito.<Long>any());
        verify(customer2).setName(Mockito.<String>any());
        verify(customer).setAddresses(Mockito.<List<Address>>any());
        verify(customer).setEmail(Mockito.<String>any());
        verify(customer, atLeast(1)).setGender(Mockito.<String>any());
        verify(customer).setId(Mockito.<Long>any());
        verify(customer, atLeast(1)).setName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#updateCustomer(Long, CustomerDto)}
     */
    @Test
    void testUpdateCustomer5() {
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        Customer customer = mock(Customer.class);
        doNothing().when(customer).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer).setEmail(Mockito.<String>any());
        doNothing().when(customer).setGender(Mockito.<String>any());
        doNothing().when(customer).setId(Mockito.<Long>any());
        doNothing().when(customer).setName(Mockito.<String>any());
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(1L, new CustomerDto()));
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(customer).setAddresses(Mockito.<List<Address>>any());
        verify(customer).setEmail(Mockito.<String>any());
        verify(customer).setGender(Mockito.<String>any());
        verify(customer).setId(Mockito.<Long>any());
        verify(customer).setName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#updateCustomer(Long, CustomerDto)}
     */
    @Test
    void testUpdateCustomer6() {
        Customer customer = mock(Customer.class);
        doNothing().when(customer).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer).setEmail(Mockito.<String>any());
        doNothing().when(customer).setGender(Mockito.<String>any());
        doNothing().when(customer).setId(Mockito.<Long>any());
        doNothing().when(customer).setName(Mockito.<String>any());
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        Customer customer2 = mock(Customer.class);
        when(customer2.getId()).thenReturn(1L);
        when(customer2.getEmail()).thenReturn("jane.doe@example.org");
        doNothing().when(customer2).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer2).setEmail(Mockito.<String>any());
        doNothing().when(customer2).setGender(Mockito.<String>any());
        doNothing().when(customer2).setId(Mockito.<Long>any());
        doNothing().when(customer2).setName(Mockito.<String>any());
        customer2.setAddresses(new ArrayList<>());
        customer2.setEmail("jane.doe@example.org");
        customer2.setGender("Gender");
        customer2.setId(1L);
        customer2.setName("Name");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer2);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        CustomerDto updatedCustomer = mock(CustomerDto.class);
        when(updatedCustomer.getGender()).thenReturn("Gender");
        when(updatedCustomer.getName()).thenReturn("Name");
        doNothing().when(updatedCustomer).setEmail(Mockito.<String>any());
        doNothing().when(updatedCustomer).setId(Mockito.<Long>any());
        assertSame(updatedCustomer, customerService.updateCustomer(1L, updatedCustomer));
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(customer2).getId();
        verify(customer2).getEmail();
        verify(customer2).setAddresses(Mockito.<List<Address>>any());
        verify(customer2).setEmail(Mockito.<String>any());
        verify(customer2).setGender(Mockito.<String>any());
        verify(customer2).setId(Mockito.<Long>any());
        verify(customer2).setName(Mockito.<String>any());
        verify(customer).setAddresses(Mockito.<List<Address>>any());
        verify(customer).setEmail(Mockito.<String>any());
        verify(customer, atLeast(1)).setGender(Mockito.<String>any());
        verify(customer).setId(Mockito.<Long>any());
        verify(customer, atLeast(1)).setName(Mockito.<String>any());
        verify(updatedCustomer).getGender();
        verify(updatedCustomer).getName();
        verify(updatedCustomer).setEmail(Mockito.<String>any());
        verify(updatedCustomer).setId(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CustomerService#deleteCustomer(Long)}
     */
    @Test
    void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        doNothing().when(customerRepository).deleteById(Mockito.<Long>any());
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        customerService.deleteCustomer(1L);
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(customerRepository).deleteById(Mockito.<Long>any());
        assertTrue(customerService.findAll().isEmpty());
    }

    /**
     * Method under test: {@link CustomerService#deleteCustomer(Long)}
     */
    @Test
    void testDeleteCustomer2() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        doThrow(new CustomerNotFoundException("An error occurred")).when(customerRepository)
                .deleteById(Mockito.<Long>any());
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(1L));
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(customerRepository).deleteById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CustomerService#deleteCustomer(Long)}
     */
    @Test
    void testDeleteCustomer3() {
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(1L));
        verify(customerRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CustomerService#addAddressToCustomer(Long, Address)}
     */
    @Test
    void testAddAddressToCustomer() {
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
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer2);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Customer customer3 = new Customer();
        customer3.setAddresses(new ArrayList<>());
        customer3.setEmail("jane.doe@example.org");
        customer3.setGender("Gender");
        customer3.setId(1L);
        customer3.setName("Name");

        Address address = new Address();
        address.setBairro("Bairro");
        address.setCep("Cep");
        address.setComplemento("alice.liddell@example.org");
        address.setCustomer(customer3);
        address.setDdd(1);
        address.setGia("Gia");
        address.setIbge("Ibge");
        address.setId(1L);
        address.setLocalidade("Localidade");
        address.setLogradouro("Logradouro");
        address.setSiafi("Siafi");
        address.setUf("Uf");
        assertSame(customer2, customerService.addAddressToCustomer(1L, address));
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository).findById(Mockito.<Long>any());
        Customer customer4 = address.getCustomer();
        assertSame(customer, customer4);
        assertEquals(1, customer4.getAddresses().size());
    }

    /**
     * Method under test: {@link CustomerService#addAddressToCustomer(Long, Address)}
     */
    @Test
    void testAddAddressToCustomer2() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.save(Mockito.<Customer>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Customer customer2 = new Customer();
        customer2.setAddresses(new ArrayList<>());
        customer2.setEmail("jane.doe@example.org");
        customer2.setGender("Gender");
        customer2.setId(1L);
        customer2.setName("Name");

        Address address = new Address();
        address.setBairro("Bairro");
        address.setCep("Cep");
        address.setComplemento("alice.liddell@example.org");
        address.setCustomer(customer2);
        address.setDdd(1);
        address.setGia("Gia");
        address.setIbge("Ibge");
        address.setId(1L);
        address.setLocalidade("Localidade");
        address.setLogradouro("Logradouro");
        address.setSiafi("Siafi");
        address.setUf("Uf");
        assertThrows(CustomerNotFoundException.class, () -> customerService.addAddressToCustomer(1L, address));
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CustomerService#addAddressToCustomer(Long, Address)}
     */
    @Test
    void testAddAddressToCustomer3() {
        Customer customer = mock(Customer.class);
        when(customer.getAddresses()).thenReturn(new ArrayList<>());
        doNothing().when(customer).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer).setEmail(Mockito.<String>any());
        doNothing().when(customer).setGender(Mockito.<String>any());
        doNothing().when(customer).setId(Mockito.<Long>any());
        doNothing().when(customer).setName(Mockito.<String>any());
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
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer2);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Customer customer3 = new Customer();
        customer3.setAddresses(new ArrayList<>());
        customer3.setEmail("jane.doe@example.org");
        customer3.setGender("Gender");
        customer3.setId(1L);
        customer3.setName("Name");

        Address address = new Address();
        address.setBairro("Bairro");
        address.setCep("Cep");
        address.setComplemento("alice.liddell@example.org");
        address.setCustomer(customer3);
        address.setDdd(1);
        address.setGia("Gia");
        address.setIbge("Ibge");
        address.setId(1L);
        address.setLocalidade("Localidade");
        address.setLogradouro("Logradouro");
        address.setSiafi("Siafi");
        address.setUf("Uf");
        assertSame(customer2, customerService.addAddressToCustomer(1L, address));
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(customer).getAddresses();
        verify(customer).setAddresses(Mockito.<List<Address>>any());
        verify(customer).setEmail(Mockito.<String>any());
        verify(customer).setGender(Mockito.<String>any());
        verify(customer).setId(Mockito.<Long>any());
        verify(customer).setName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#addAddressToCustomer(Long, Address)}
     */
    @Test
    void testAddAddressToCustomer4() {
        Optional<Customer> emptyResult = Optional.empty();
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");

        Address address = new Address();
        address.setBairro("Bairro");
        address.setCep("Cep");
        address.setComplemento("alice.liddell@example.org");
        address.setCustomer(customer);
        address.setDdd(1);
        address.setGia("Gia");
        address.setIbge("Ibge");
        address.setId(1L);
        address.setLocalidade("Localidade");
        address.setLogradouro("Logradouro");
        address.setSiafi("Siafi");
        address.setUf("Uf");
        assertThrows(CustomerNotFoundException.class, () -> customerService.addAddressToCustomer(1L, address));
        verify(customerRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link CustomerService#addAddressToCustomer(Long, Address)}
     */
    @Test
    void testAddAddressToCustomer5() {
        Customer customer = mock(Customer.class);
        when(customer.getAddresses()).thenReturn(new ArrayList<>());
        doNothing().when(customer).setAddresses(Mockito.<List<Address>>any());
        doNothing().when(customer).setEmail(Mockito.<String>any());
        doNothing().when(customer).setGender(Mockito.<String>any());
        doNothing().when(customer).setId(Mockito.<Long>any());
        doNothing().when(customer).setName(Mockito.<String>any());
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
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer2);
        when(customerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Customer customer3 = new Customer();
        customer3.setAddresses(new ArrayList<>());
        customer3.setEmail("jane.doe@example.org");
        customer3.setGender("Gender");
        customer3.setId(1L);
        customer3.setName("Name");
        Address address = mock(Address.class);
        doNothing().when(address).setBairro(Mockito.<String>any());
        doNothing().when(address).setCep(Mockito.<String>any());
        doNothing().when(address).setComplemento(Mockito.<String>any());
        doNothing().when(address).setCustomer(Mockito.<Customer>any());
        doNothing().when(address).setDdd(Mockito.<Integer>any());
        doNothing().when(address).setGia(Mockito.<String>any());
        doNothing().when(address).setIbge(Mockito.<String>any());
        doNothing().when(address).setId(Mockito.<Long>any());
        doNothing().when(address).setLocalidade(Mockito.<String>any());
        doNothing().when(address).setLogradouro(Mockito.<String>any());
        doNothing().when(address).setSiafi(Mockito.<String>any());
        doNothing().when(address).setUf(Mockito.<String>any());
        address.setBairro("Bairro");
        address.setCep("Cep");
        address.setComplemento("alice.liddell@example.org");
        address.setCustomer(customer3);
        address.setDdd(1);
        address.setGia("Gia");
        address.setIbge("Ibge");
        address.setId(1L);
        address.setLocalidade("Localidade");
        address.setLogradouro("Logradouro");
        address.setSiafi("Siafi");
        address.setUf("Uf");
        assertSame(customer2, customerService.addAddressToCustomer(1L, address));
        verify(customerRepository).save(Mockito.<Customer>any());
        verify(customerRepository).findById(Mockito.<Long>any());
        verify(customer).getAddresses();
        verify(customer).setAddresses(Mockito.<List<Address>>any());
        verify(customer).setEmail(Mockito.<String>any());
        verify(customer).setGender(Mockito.<String>any());
        verify(customer).setId(Mockito.<Long>any());
        verify(customer).setName(Mockito.<String>any());
        verify(address).setBairro(Mockito.<String>any());
        verify(address).setCep(Mockito.<String>any());
        verify(address).setComplemento(Mockito.<String>any());
        verify(address, atLeast(1)).setCustomer(Mockito.<Customer>any());
        verify(address).setDdd(Mockito.<Integer>any());
        verify(address).setGia(Mockito.<String>any());
        verify(address).setIbge(Mockito.<String>any());
        verify(address).setId(Mockito.<Long>any());
        verify(address).setLocalidade(Mockito.<String>any());
        verify(address).setLogradouro(Mockito.<String>any());
        verify(address).setSiafi(Mockito.<String>any());
        verify(address).setUf(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#updateCustomerAddress(Customer)}
     */
    @Test
    void testUpdateCustomerAddress() {
        Customer customer = new Customer();
        ArrayList<Address> addresses = new ArrayList<>();
        customer.setAddresses(addresses);
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer);

        Customer customer2 = new Customer();
        customer2.setAddresses(new ArrayList<>());
        customer2.setEmail("jane.doe@example.org");
        customer2.setGender("Gender");
        customer2.setId(1L);
        customer2.setName("Name");
        customerService.updateCustomerAddress(customer2);
        verify(customerRepository).save(Mockito.<Customer>any());
        assertEquals(addresses, customer2.getAddresses());
        assertEquals("Name", customer2.getName());
        assertEquals(1L, customer2.getId().longValue());
        assertEquals("Gender", customer2.getGender());
        assertEquals("jane.doe@example.org", customer2.getEmail());
        assertTrue(customerService.findAll().isEmpty());
    }

    /**
     * Method under test: {@link CustomerService#updateCustomerAddress(Customer)}
     */
    @Test
    void testUpdateCustomerAddress2() {
        when(customerRepository.save(Mockito.<Customer>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));

        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomerAddress(customer));
        verify(customerRepository).save(Mockito.<Customer>any());
    }

    /**
     * Method under test: {@link CustomerService#fixToIntDDD(String)}
     */
    @Test
    void testFixToIntDDD() {
        assertEquals(0, customerService.fixToIntDDD("Ddd").intValue());
        assertEquals(42, customerService.fixToIntDDD("42").intValue());
    }

    /**
     * Method under test: {@link CustomerService#filterCustomersByLocationAndUf(String, String)}
     */
    @Test
    void testFilterCustomersByLocationAndUf() {
        when(customerRepository.findByAddressesLocalidadeAndAddressesUf(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        assertTrue(customerService.filterCustomersByLocationAndUf("Localidade", "Uf").isEmpty());
        verify(customerRepository).findByAddressesLocalidadeAndAddressesUf(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#filterCustomersByLocationAndUf(String, String)}
     */
    @Test
    void testFilterCustomersByLocationAndUf2() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerRepository.findByAddressesLocalidadeAndAddressesUf(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(customerList);
        List<CustomerDto> actualFilterCustomersByLocationAndUfResult = customerService
                .filterCustomersByLocationAndUf("Localidade", "Uf");
        assertEquals(1, actualFilterCustomersByLocationAndUfResult.size());
        CustomerDto getResult = actualFilterCustomersByLocationAndUfResult.get(0);
        assertTrue(getResult.getAddresses().isEmpty());
        assertEquals("Name", getResult.getName());
        assertEquals(1L, getResult.getId().longValue());
        assertEquals("Gender", getResult.getGender());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        verify(customerRepository).findByAddressesLocalidadeAndAddressesUf(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#filterCustomersByLocationAndUf(String, String)}
     */
    @Test
    void testFilterCustomersByLocationAndUf3() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");

        Customer customer2 = new Customer();
        ArrayList<Address> addresses = new ArrayList<>();
        customer2.setAddresses(addresses);
        customer2.setEmail("john.smith@example.org");
        customer2.setGender("42");
        customer2.setId(2L);
        customer2.setName("42");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer2);
        customerList.add(customer);
        when(customerRepository.findByAddressesLocalidadeAndAddressesUf(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(customerList);
        List<CustomerDto> actualFilterCustomersByLocationAndUfResult = customerService
                .filterCustomersByLocationAndUf("Localidade", "Uf");
        assertEquals(2, actualFilterCustomersByLocationAndUfResult.size());
        CustomerDto getResult = actualFilterCustomersByLocationAndUfResult.get(0);
        assertEquals("42", getResult.getName());
        CustomerDto getResult2 = actualFilterCustomersByLocationAndUfResult.get(1);
        assertEquals("Name", getResult2.getName());
        assertEquals(1L, getResult2.getId().longValue());
        assertEquals("Gender", getResult2.getGender());
        assertEquals("jane.doe@example.org", getResult2.getEmail());
        assertEquals(addresses, getResult2.getAddresses());
        assertEquals(2L, getResult.getId().longValue());
        assertEquals("42", getResult.getGender());
        assertEquals("john.smith@example.org", getResult.getEmail());
        assertTrue(getResult.getAddresses().isEmpty());
        verify(customerRepository).findByAddressesLocalidadeAndAddressesUf(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#filterCustomersByLocationAndUf(String, String)}
     */
    @Test
    void testFilterCustomersByLocationAndUf4() {
        when(customerRepository.findByAddressesUf(Mockito.<String>any())).thenReturn(new ArrayList<>());
        assertTrue(customerService.filterCustomersByLocationAndUf(null, "Uf").isEmpty());
        verify(customerRepository).findByAddressesUf(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#filterCustomersByLocationAndUf(String, String)}
     */
    @Test
    void testFilterCustomersByLocationAndUf5() {
        assertTrue(customerService.filterCustomersByLocationAndUf(null, null).isEmpty());
    }

    /**
     * Method under test: {@link CustomerService#filterCustomersByLocationAndUf(String, String)}
     */
    @Test
    void testFilterCustomersByLocationAndUf6() {
        when(customerRepository.findByAddressesUf(Mockito.<String>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        assertThrows(CustomerNotFoundException.class, () -> customerService.filterCustomersByLocationAndUf(null, "Uf"));
        verify(customerRepository).findByAddressesUf(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#filterCustomersByLocationAndUf(String, String)}
     */
    @Test
    void testFilterCustomersByLocationAndUf7() {
        when(customerRepository.findByAddressesLocalidade(Mockito.<String>any())).thenReturn(new ArrayList<>());
        assertTrue(customerService.filterCustomersByLocationAndUf("Localidade", null).isEmpty());
        verify(customerRepository).findByAddressesLocalidade(Mockito.<String>any());
    }

    /**
     * Method under test: {@link CustomerService#filterCustomersByLocationAndUf(String, String)}
     */
    @Test
    void testFilterCustomersByLocationAndUf8() {
        when(customerRepository.findByAddressesLocalidade(Mockito.<String>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.filterCustomersByLocationAndUf("Localidade", null));
        verify(customerRepository).findByAddressesLocalidade(Mockito.<String>any());
    }
}

