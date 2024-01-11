package com.example.api.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class AddressTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Address#Address()}
     *   <li>{@link Address#setBairro(String)}
     *   <li>{@link Address#setCep(String)}
     *   <li>{@link Address#setComplemento(String)}
     *   <li>{@link Address#setCustomer(Customer)}
     *   <li>{@link Address#setDdd(Integer)}
     *   <li>{@link Address#setGia(String)}
     *   <li>{@link Address#setIbge(String)}
     *   <li>{@link Address#setId(Long)}
     *   <li>{@link Address#setLocalidade(String)}
     *   <li>{@link Address#setLogradouro(String)}
     *   <li>{@link Address#setSiafi(String)}
     *   <li>{@link Address#setUf(String)}
     *   <li>{@link Address#getBairro()}
     *   <li>{@link Address#getCep()}
     *   <li>{@link Address#getComplemento()}
     *   <li>{@link Address#getCustomer()}
     *   <li>{@link Address#getDdd()}
     *   <li>{@link Address#getGia()}
     *   <li>{@link Address#getIbge()}
     *   <li>{@link Address#getId()}
     *   <li>{@link Address#getLocalidade()}
     *   <li>{@link Address#getLogradouro()}
     *   <li>{@link Address#getSiafi()}
     *   <li>{@link Address#getUf()}
     * </ul>
     */
    @Test
    void testConstructor() {
        Address actualAddress = new Address();
        actualAddress.setBairro("Bairro");
        actualAddress.setCep("Cep");
        actualAddress.setComplemento("alice.liddell@example.org");
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        actualAddress.setCustomer(customer);
        actualAddress.setDdd(1);
        actualAddress.setGia("Gia");
        actualAddress.setIbge("Ibge");
        actualAddress.setId(1L);
        actualAddress.setLocalidade("Localidade");
        actualAddress.setLogradouro("Logradouro");
        actualAddress.setSiafi("Siafi");
        actualAddress.setUf("Uf");
        String actualBairro = actualAddress.getBairro();
        String actualCep = actualAddress.getCep();
        String actualComplemento = actualAddress.getComplemento();
        Customer actualCustomer = actualAddress.getCustomer();
        Integer actualDdd = actualAddress.getDdd();
        String actualGia = actualAddress.getGia();
        String actualIbge = actualAddress.getIbge();
        Long actualId = actualAddress.getId();
        String actualLocalidade = actualAddress.getLocalidade();
        String actualLogradouro = actualAddress.getLogradouro();
        String actualSiafi = actualAddress.getSiafi();
        String actualUf = actualAddress.getUf();
        assertEquals("Bairro", actualBairro);
        assertEquals("Cep", actualCep);
        assertEquals("alice.liddell@example.org", actualComplemento);
        assertSame(customer, actualCustomer);
        assertEquals(1, actualDdd.intValue());
        assertEquals("Gia", actualGia);
        assertEquals("Ibge", actualIbge);
        assertEquals(1L, actualId.longValue());
        assertEquals("Localidade", actualLocalidade);
        assertEquals("Logradouro", actualLogradouro);
        assertEquals("Siafi", actualSiafi);
        assertEquals("Uf", actualUf);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Address#Address(Long, String, String, String, String, String, String, String, String, Integer, String, Customer)}
     *   <li>{@link Address#setBairro(String)}
     *   <li>{@link Address#setCep(String)}
     *   <li>{@link Address#setComplemento(String)}
     *   <li>{@link Address#setCustomer(Customer)}
     *   <li>{@link Address#setDdd(Integer)}
     *   <li>{@link Address#setGia(String)}
     *   <li>{@link Address#setIbge(String)}
     *   <li>{@link Address#setId(Long)}
     *   <li>{@link Address#setLocalidade(String)}
     *   <li>{@link Address#setLogradouro(String)}
     *   <li>{@link Address#setSiafi(String)}
     *   <li>{@link Address#setUf(String)}
     *   <li>{@link Address#getBairro()}
     *   <li>{@link Address#getCep()}
     *   <li>{@link Address#getComplemento()}
     *   <li>{@link Address#getCustomer()}
     *   <li>{@link Address#getDdd()}
     *   <li>{@link Address#getGia()}
     *   <li>{@link Address#getIbge()}
     *   <li>{@link Address#getId()}
     *   <li>{@link Address#getLocalidade()}
     *   <li>{@link Address#getLogradouro()}
     *   <li>{@link Address#getSiafi()}
     *   <li>{@link Address#getUf()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        Customer customer = new Customer();
        customer.setAddresses(new ArrayList<>());
        customer.setEmail("jane.doe@example.org");
        customer.setGender("Gender");
        customer.setId(1L);
        customer.setName("Name");
        Address actualAddress = new Address(1L, "Cep", "Logradouro", "alice.liddell@example.org", "Bairro", "Localidade",
                "Uf", "Ibge", "Gia", 1, "Siafi", customer);
        actualAddress.setBairro("Bairro");
        actualAddress.setCep("Cep");
        actualAddress.setComplemento("alice.liddell@example.org");
        Customer customer2 = new Customer();
        customer2.setAddresses(new ArrayList<>());
        customer2.setEmail("jane.doe@example.org");
        customer2.setGender("Gender");
        customer2.setId(1L);
        customer2.setName("Name");
        actualAddress.setCustomer(customer2);
        actualAddress.setDdd(1);
        actualAddress.setGia("Gia");
        actualAddress.setIbge("Ibge");
        actualAddress.setId(1L);
        actualAddress.setLocalidade("Localidade");
        actualAddress.setLogradouro("Logradouro");
        actualAddress.setSiafi("Siafi");
        actualAddress.setUf("Uf");
        String actualBairro = actualAddress.getBairro();
        String actualCep = actualAddress.getCep();
        String actualComplemento = actualAddress.getComplemento();
        Customer actualCustomer = actualAddress.getCustomer();
        Integer actualDdd = actualAddress.getDdd();
        String actualGia = actualAddress.getGia();
        String actualIbge = actualAddress.getIbge();
        Long actualId = actualAddress.getId();
        String actualLocalidade = actualAddress.getLocalidade();
        String actualLogradouro = actualAddress.getLogradouro();
        String actualSiafi = actualAddress.getSiafi();
        String actualUf = actualAddress.getUf();
        assertEquals("Bairro", actualBairro);
        assertEquals("Cep", actualCep);
        assertEquals("alice.liddell@example.org", actualComplemento);
        assertSame(customer2, actualCustomer);
        assertEquals(1, actualDdd.intValue());
        assertEquals("Gia", actualGia);
        assertEquals("Ibge", actualIbge);
        assertEquals(1L, actualId.longValue());
        assertEquals("Localidade", actualLocalidade);
        assertEquals("Logradouro", actualLogradouro);
        assertEquals("Siafi", actualSiafi);
        assertEquals("Uf", actualUf);
    }
}

