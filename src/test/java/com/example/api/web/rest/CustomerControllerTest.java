package com.example.api.web.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.api.DTO.CustomerDto;
import com.example.api.DTO.CustomerRequestDto;
import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.exceptions.CustomerNotFoundException;
import com.example.api.integration.ViaCepIntegration;
import com.example.api.repository.CustomerRepository;
import com.example.api.service.CustomerService;
import com.example.api.service.ViaCepService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CustomerController.class})
@ExtendWith(SpringExtension.class)
class CustomerControllerTest {
    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;

    /**
     * Method under test: {@link CustomerController#findAll()}
     */
    @Test
    void testFindAll() throws Exception {
        when(customerService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CustomerController#findAll()}
     */
    @Test
    void testFindAll2() throws Exception {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerService.findAll()).thenReturn(customerList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"gender\":\"Gender\",\"addresses\":[]}]"));
    }

    /**
     * Method under test: {@link CustomerController#findAll()}
     */
    @Test
    void testFindAll3() throws Exception {
        when(customerService.findAll()).thenThrow(new CustomerNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CustomerController#findAll()}
     */
    @Test
    void testFindAll4() throws Exception {
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

        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(address);

        Customer customer2 = new Customer();
        customer2.setAddresses(addresses);
        customer2.setEmail("jane.doe@example.org");
        customer2.setGender("Gender");
        customer2.setId(1L);
        customer2.setName("Name");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer2);
        when(customerService.findAll()).thenReturn(customerList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"gender\":\"Gender\",\"addresses\":[{\"id\":1,\"cep\":"
                                        + "\"Cep\",\"logradouro\":\"Logradouro\",\"complemento\":\"alice.liddell@example.org\",\"bairro\":\"Bairro\",\"localidade"
                                        + "\":\"Localidade\",\"uf\":\"Uf\",\"ibge\":\"Ibge\",\"gia\":\"Gia\",\"ddd\":1,\"siafi\":\"Siafi\"}]}]"));
    }

    /**
     * Method under test: {@link CustomerController#searchByName(String)}
     */
    @Test
    void testSearchByName() throws Exception {
        when(customerService.searchByName(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/searchByName")
                .param("name", "foo");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CustomerController#searchByName(String)}
     */
    @Test
    void testSearchByName2() throws Exception {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("?");
        customer.setId(1L);
        customer.setName("?");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerService.searchByName(Mockito.<String>any())).thenReturn(customerList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/searchByName")
                .param("name", "foo");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"name\":\"?\",\"email\":\"jane.doe@example.org\",\"gender\":\"?\",\"addresses\":[]}]"));
    }

    /**
     * Method under test: {@link CustomerController#searchByName(String)}
     */
    @Test
    void testSearchByName3() throws Exception {
        when(customerService.searchByName(Mockito.<String>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/searchByName")
                .param("name", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CustomerController#searchByName(String)}
     */
    @Test
    void testSearchByName4() throws Exception {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("?");
        customer.setId(1L);
        customer.setName("?");

        Address address = new Address();
        address.setBairro("?");
        address.setCep("?");
        address.setComplemento("alice.liddell@example.org");
        address.setCustomer(customer);
        address.setDdd(1);
        address.setGia("?");
        address.setIbge("?");
        address.setId(1L);
        address.setLocalidade("?");
        address.setLogradouro("?");
        address.setSiafi("?");
        address.setUf("?");

        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(address);

        Customer customer2 = new Customer();
        customer2.setAddresses(addresses);
        customer2.setEmail("jane.doe@example.org");
        customer2.setGender("?");
        customer2.setId(1L);
        customer2.setName("?");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer2);
        when(customerService.searchByName(Mockito.<String>any())).thenReturn(customerList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/searchByName")
                .param("name", "foo");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"name\":\"?\",\"email\":\"jane.doe@example.org\",\"gender\":\"?\",\"addresses\":[{\"id\":1,\"cep\":\"?\","
                                        + "\"logradouro\":\"?\",\"complemento\":\"alice.liddell@example.org\",\"bairro\":\"?\",\"localidade\":\"?\",\"uf\":\"?\","
                                        + "\"ibge\":\"?\",\"gia\":\"?\",\"ddd\":1,\"siafi\":\"?\"}]}]"));
    }

    /**
     * Method under test: {@link CustomerController#searchByEmail(String)}
     */
    @Test
    void testSearchByEmail() throws Exception {
        when(customerService.searchByEmail(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/searchByEmail")
                .param("email", "foo");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CustomerController#searchByEmail(String)}
     */
    @Test
    void testSearchByEmail2() throws Exception {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("?");
        customer.setId(1L);
        customer.setName("?");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerService.searchByEmail(Mockito.<String>any())).thenReturn(customerList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/searchByEmail")
                .param("email", "foo");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"name\":\"?\",\"email\":\"jane.doe@example.org\",\"gender\":\"?\",\"addresses\":[]}]"));
    }

    /**
     * Method under test: {@link CustomerController#searchByEmail(String)}
     */
    @Test
    void testSearchByEmail3() throws Exception {
        when(customerService.searchByEmail(Mockito.<String>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/searchByEmail")
                .param("email", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CustomerController#searchByEmail(String)}
     */
    @Test
    void testSearchByEmail4() throws Exception {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("?");
        customer.setId(1L);
        customer.setName("?");

        Address address = new Address();
        address.setBairro("?");
        address.setCep("?");
        address.setComplemento("alice.liddell@example.org");
        address.setCustomer(customer);
        address.setDdd(1);
        address.setGia("?");
        address.setIbge("?");
        address.setId(1L);
        address.setLocalidade("?");
        address.setLogradouro("?");
        address.setSiafi("?");
        address.setUf("?");

        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(address);

        Customer customer2 = new Customer();
        customer2.setAddresses(addresses);
        customer2.setEmail("jane.doe@example.org");
        customer2.setGender("?");
        customer2.setId(1L);
        customer2.setName("?");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer2);
        when(customerService.searchByEmail(Mockito.<String>any())).thenReturn(customerList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/searchByEmail")
                .param("email", "foo");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"name\":\"?\",\"email\":\"jane.doe@example.org\",\"gender\":\"?\",\"addresses\":[{\"id\":1,\"cep\":\"?\","
                                        + "\"logradouro\":\"?\",\"complemento\":\"alice.liddell@example.org\",\"bairro\":\"?\",\"localidade\":\"?\",\"uf\":\"?\","
                                        + "\"ibge\":\"?\",\"gia\":\"?\",\"ddd\":1,\"siafi\":\"?\"}]}]"));
    }

    /**
     * Method under test: {@link CustomerController#searchByGender(String)}
     */
    @Test
    void testSearchByGender() throws Exception {
        when(customerService.searchByGender(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/searchByGender")
                .param("gender", "foo");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CustomerController#searchByGender(String)}
     */
    @Test
    void testSearchByGender2() throws Exception {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("?");
        customer.setId(1L);
        customer.setName("?");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(customerService.searchByGender(Mockito.<String>any())).thenReturn(customerList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/searchByGender")
                .param("gender", "foo");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"name\":\"?\",\"email\":\"jane.doe@example.org\",\"gender\":\"?\",\"addresses\":[]}]"));
    }

    /**
     * Method under test: {@link CustomerController#searchByGender(String)}
     */
    @Test
    void testSearchByGender3() throws Exception {
        when(customerService.searchByGender(Mockito.<String>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/searchByGender")
                .param("gender", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CustomerController#searchByGender(String)}
     */
    @Test
    void testSearchByGender4() throws Exception {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("?");
        customer.setId(1L);
        customer.setName("?");

        Address address = new Address();
        address.setBairro("?");
        address.setCep("?");
        address.setComplemento("alice.liddell@example.org");
        address.setCustomer(customer);
        address.setDdd(1);
        address.setGia("?");
        address.setIbge("?");
        address.setId(1L);
        address.setLocalidade("?");
        address.setLogradouro("?");
        address.setSiafi("?");
        address.setUf("?");

        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(address);

        Customer customer2 = new Customer();
        customer2.setAddresses(addresses);
        customer2.setEmail("jane.doe@example.org");
        customer2.setGender("?");
        customer2.setId(1L);
        customer2.setName("?");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer2);
        when(customerService.searchByGender(Mockito.<String>any())).thenReturn(customerList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/searchByGender")
                .param("gender", "foo");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"name\":\"?\",\"email\":\"jane.doe@example.org\",\"gender\":\"?\",\"addresses\":[{\"id\":1,\"cep\":\"?\","
                                        + "\"logradouro\":\"?\",\"complemento\":\"alice.liddell@example.org\",\"bairro\":\"?\",\"localidade\":\"?\",\"uf\":\"?\","
                                        + "\"ibge\":\"?\",\"gia\":\"?\",\"ddd\":1,\"siafi\":\"?\"}]}]"));
    }

    /**
     * Method under test: {@link CustomerController#searchCustomers(String, String, String, Pageable)}
     */
    @Test
    void testSearchCustomers() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalStateException: No primary or single unique constructor found for interface org.springframework.data.domain.Pageable
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:655)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //   See https://diff.blue/R013 to resolve this issue.

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        ResponseEntity<Page<CustomerDto>> actualSearchCustomersResult = (new CustomerController(
                new CustomerService(customerRepository, new ViaCepService(mock(ViaCepIntegration.class)))))
                .searchCustomers("Name", "jane.doe@example.org", "Gender", null);
        assertTrue(actualSearchCustomersResult.hasBody());
        assertTrue(actualSearchCustomersResult.getBody().toList().isEmpty());
        assertEquals(HttpStatus.OK, actualSearchCustomersResult.getStatusCode());
        assertTrue(actualSearchCustomersResult.getHeaders().isEmpty());
        verify(customerRepository).findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link CustomerController#searchCustomers(String, String, String, Pageable)}
     */
    @Test
    void testSearchCustomers2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalStateException: No primary or single unique constructor found for interface org.springframework.data.domain.Pageable
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:655)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");

        ArrayList<Customer> content = new ArrayList<>();
        content.add(customer);
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any())).thenReturn(new PageImpl<>(content));
        ResponseEntity<Page<CustomerDto>> actualSearchCustomersResult = (new CustomerController(
                new CustomerService(customerRepository, new ViaCepService(mock(ViaCepIntegration.class)))))
                .searchCustomers("Name", "jane.doe@example.org", "Gender", null);
        assertTrue(actualSearchCustomersResult.hasBody());
        List<CustomerDto> toListResult = actualSearchCustomersResult.getBody().toList();
        assertEquals(1, toListResult.size());
        assertEquals(HttpStatus.OK, actualSearchCustomersResult.getStatusCode());
        assertTrue(actualSearchCustomersResult.getHeaders().isEmpty());
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
     * Method under test: {@link CustomerController#searchCustomers(String, String, String, Pageable)}
     */
    @Test
    void testSearchCustomers3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalStateException: No primary or single unique constructor found for interface org.springframework.data.domain.Pageable
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:655)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //   See https://diff.blue/R013 to resolve this issue.

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
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findByAttributes(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any())).thenReturn(new PageImpl<>(content));
        ResponseEntity<Page<CustomerDto>> actualSearchCustomersResult = (new CustomerController(
                new CustomerService(customerRepository, new ViaCepService(mock(ViaCepIntegration.class)))))
                .searchCustomers("Name", "jane.doe@example.org", "Gender", null);
        assertTrue(actualSearchCustomersResult.hasBody());
        List<CustomerDto> toListResult = actualSearchCustomersResult.getBody().toList();
        assertEquals(2, toListResult.size());
        assertEquals(HttpStatus.OK, actualSearchCustomersResult.getStatusCode());
        assertTrue(actualSearchCustomersResult.getHeaders().isEmpty());
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
     * Method under test: {@link CustomerController#searchCustomers(String, String, String, Pageable)}
     */
    @Test
    void testSearchCustomers4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalStateException: No primary or single unique constructor found for interface org.springframework.data.domain.Pageable
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:655)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:764)
        //   See https://diff.blue/R013 to resolve this issue.

        CustomerService service = mock(CustomerService.class);
        when(service.searchCustomers(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        ResponseEntity<Page<CustomerDto>> actualSearchCustomersResult = (new CustomerController(service))
                .searchCustomers("Name", "jane.doe@example.org", "Gender", null);
        assertTrue(actualSearchCustomersResult.hasBody());
        assertTrue(actualSearchCustomersResult.getBody().toList().isEmpty());
        assertEquals(HttpStatus.OK, actualSearchCustomersResult.getStatusCode());
        assertTrue(actualSearchCustomersResult.getHeaders().isEmpty());
        verify(service).searchCustomers(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Pageable>any());
    }

    /**
     * Method under test: {@link CustomerController#updateCustomer(Long, CustomerDto)}
     */
    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDto.CustomerDtoBuilder builderResult = CustomerDto.builder();
        when(customerService.updateCustomer(Mockito.<Long>any(), Mockito.<CustomerDto>any()))
                .thenReturn(builderResult.addresses(new ArrayList<>())
                        .email("jane.doe@example.org")
                        .gender("Gender")
                        .id(1L)
                        .name("Name")
                        .build());

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAddresses(new ArrayList<>());
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setGender("Gender");
        customerDto.setId(1L);
        customerDto.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customers/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"gender\":\"Gender\",\"addresses\":[]}"));
    }

    /**
     * Method under test: {@link CustomerController#updateCustomer(Long, CustomerDto)}
     */
    @Test
    void testUpdateCustomer2() throws Exception {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("?");
        customer.setId(1L);
        customer.setName("?");

        Address address = new Address();
        address.setBairro("?");
        address.setCep("?");
        address.setComplemento("alice.liddell@example.org");
        address.setCustomer(customer);
        address.setDdd(1);
        address.setGia("?");
        address.setIbge("?");
        address.setId(1L);
        address.setLocalidade("?");
        address.setLogradouro("?");
        address.setSiafi("?");
        address.setUf("?");

        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(address);
        CustomerDto buildResult = CustomerDto.builder()
                .addresses(addresses)
                .email("jane.doe@example.org")
                .gender("Gender")
                .id(1L)
                .name("Name")
                .build();
        when(customerService.updateCustomer(Mockito.<Long>any(), Mockito.<CustomerDto>any())).thenReturn(buildResult);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAddresses(new ArrayList<>());
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setGender("Gender");
        customerDto.setId(1L);
        customerDto.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customers/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"gender\":\"Gender\",\"addresses\":[{\"id\":1,\"cep\":\"?"
                                        + "\",\"logradouro\":\"?\",\"complemento\":\"alice.liddell@example.org\",\"bairro\":\"?\",\"localidade\":\"?\",\"uf\":\"?\","
                                        + "\"ibge\":\"?\",\"gia\":\"?\",\"ddd\":1,\"siafi\":\"?\"}]}"));
    }

    /**
     * Method under test: {@link CustomerController#updateCustomer(Long, CustomerDto)}
     */
    @Test
    void testUpdateCustomer3() throws Exception {
        CustomerDto.CustomerDtoBuilder builderResult = CustomerDto.builder();
        when(customerService.updateCustomer(Mockito.<Long>any(), Mockito.<CustomerDto>any()))
                .thenReturn(builderResult.addresses(new ArrayList<>())
                        .email("jane.doe@example.org")
                        .gender("Gender")
                        .id(1L)
                        .name("Name")
                        .build());

        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("?");
        customer.setId(1L);
        customer.setName("?");

        Address address = new Address();
        address.setBairro("?");
        address.setCep("?");
        address.setComplemento("alice.liddell@example.org");
        address.setCustomer(customer);
        address.setDdd(1);
        address.setGia("?");
        address.setIbge("?");
        address.setId(1L);
        address.setLocalidade("?");
        address.setLogradouro("?");
        address.setSiafi("?");
        address.setUf("?");

        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(address);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAddresses(addresses);
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setGender("Gender");
        customerDto.setId(1L);
        customerDto.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customers/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"gender\":\"Gender\",\"addresses\":[]}"));
    }

    /**
     * Method under test: {@link CustomerController#updateCustomer(Long, CustomerDto)}
     */
    @Test
    void testUpdateCustomer4() throws Exception {
        when(customerService.updateCustomer(Mockito.<Long>any(), Mockito.<CustomerDto>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));

        CustomerDto customerDto = new CustomerDto();
        customerDto.setAddresses(new ArrayList<>());
        customerDto.setEmail("jane.doe@example.org");
        customerDto.setGender("Gender");
        customerDto.setId(1L);
        customerDto.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/customers/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CustomerController#createCustomer(CustomerRequestDto)}
     */
    @Test
    void testCreateCustomer() throws Exception {
        CustomerRequestDto customerRequestDto = new CustomerRequestDto();
        customerRequestDto.setCep("Cep");
        customerRequestDto.setEmail("jane.doe@example.org");
        customerRequestDto.setGender("Gender");
        customerRequestDto.setName("Name");
        String content = (new ObjectMapper()).writeValueAsString(customerRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customers/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link CustomerController#deleteCustomer(Long)}
     */
    @Test
    void testDeleteCustomer() throws Exception {
        doNothing().when(customerService).deleteCustomer(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/customers/delete/{id}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link CustomerController#deleteCustomer(Long)}
     */
    @Test
    void testDeleteCustomer2() throws Exception {
        doThrow(new CustomerNotFoundException("An error occurred")).when(customerService)
                .deleteCustomer(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/customers/delete/{id}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CustomerController#deleteCustomer(Long)}
     */
    @Test
    void testDeleteCustomer3() throws Exception {
        doNothing().when(customerService).deleteCustomer(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/customers/delete/{id}", 1L);
        requestBuilder.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link CustomerController#filterCustomersByLocationAndUf(String, String)}
     */
    @Test
    void testFilterCustomersByLocationAndUf() throws Exception {
        when(customerService.filterCustomersByLocationAndUf(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/filterByCiteAndState")
                .param("estado", "foo");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link CustomerController#filterCustomersByLocationAndUf(String, String)}
     */
    @Test
    void testFilterCustomersByLocationAndUf2() throws Exception {
        ArrayList<CustomerDto> customerDtoList = new ArrayList<>();
        customerDtoList
                .add(CustomerDto.builder().email("jane.doe@example.org").gender("Gender").id(1L).name("Name").build());
        when(customerService.filterCustomersByLocationAndUf(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(customerDtoList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/filterByCiteAndState")
                .param("estado", "foo");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"gender\":\"Gender\",\"addresses\":null}]"));
    }

    /**
     * Method under test: {@link CustomerController#filterCustomersByLocationAndUf(String, String)}
     */
    @Test
    void testFilterCustomersByLocationAndUf3() throws Exception {
        when(customerService.filterCustomersByLocationAndUf(Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/filterByCiteAndState")
                .param("estado", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link CustomerController#findById(Long)}
     */
    @Test
    void testFindById() throws Exception {
        when(customerService.getCustomer(Mockito.<Long>any()))
                .thenReturn(CustomerDto.builder().email("jane.doe@example.org").gender("Gender").id(1L).name("Name").build());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/{id}", 1L);
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"email\":\"jane.doe@example.org\",\"gender\":\"Gender\",\"addresses\":null}"));
    }

    /**
     * Method under test: {@link CustomerController#findById(Long)}
     */
    @Test
    void testFindById2() throws Exception {
        when(customerService.getCustomer(Mockito.<Long>any()))
                .thenReturn(CustomerDto.builder().email("jane.doe@example.org").gender("Gender").id(1L).name("Name").build());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/{id}", 1L);
        requestBuilder.accept("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
    }

    /**
     * Method under test: {@link CustomerController#findById(Long)}
     */
    @Test
    void testFindById3() throws Exception {
        when(customerService.getCustomer(Mockito.<Long>any()))
                .thenThrow(new CustomerNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customers/{id}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

