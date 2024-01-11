package com.example.api.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CustomerTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Customer#Customer()}
     *   <li>{@link Customer#setAddresses(List)}
     *   <li>{@link Customer#setEmail(String)}
     *   <li>{@link Customer#setGender(String)}
     *   <li>{@link Customer#setId(Long)}
     *   <li>{@link Customer#setName(String)}
     *   <li>{@link Customer#getAddresses()}
     *   <li>{@link Customer#getEmail()}
     *   <li>{@link Customer#getGender()}
     *   <li>{@link Customer#getId()}
     *   <li>{@link Customer#getName()}
     * </ul>
     */
    @Test
    void testConstructor() {
        Customer actualCustomer = new Customer();
        ArrayList<Address> addresses = new ArrayList<>();
        actualCustomer.setAddresses(addresses);
        actualCustomer.setEmail("jane.doe@example.org");
        actualCustomer.setGender("Gender");
        actualCustomer.setId(1L);
        actualCustomer.setName("Name");
        List<Address> actualAddresses = actualCustomer.getAddresses();
        String actualEmail = actualCustomer.getEmail();
        String actualGender = actualCustomer.getGender();
        Long actualId = actualCustomer.getId();
        String actualName = actualCustomer.getName();
        assertSame(addresses, actualAddresses);
        assertEquals("jane.doe@example.org", actualEmail);
        assertEquals("Gender", actualGender);
        assertEquals(1L, actualId.longValue());
        assertEquals("Name", actualName);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link Customer#Customer(Long, String, String, String, List)}
     *   <li>{@link Customer#setAddresses(List)}
     *   <li>{@link Customer#setEmail(String)}
     *   <li>{@link Customer#setGender(String)}
     *   <li>{@link Customer#setId(Long)}
     *   <li>{@link Customer#setName(String)}
     *   <li>{@link Customer#getAddresses()}
     *   <li>{@link Customer#getEmail()}
     *   <li>{@link Customer#getGender()}
     *   <li>{@link Customer#getId()}
     *   <li>{@link Customer#getName()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        ArrayList<Address> addresses = new ArrayList<>();
        Customer actualCustomer = new Customer(1L, "Name", "jane.doe@example.org", "Gender", addresses);
        ArrayList<Address> addresses2 = new ArrayList<>();
        actualCustomer.setAddresses(addresses2);
        actualCustomer.setEmail("jane.doe@example.org");
        actualCustomer.setGender("Gender");
        actualCustomer.setId(1L);
        actualCustomer.setName("Name");
        List<Address> actualAddresses = actualCustomer.getAddresses();
        String actualEmail = actualCustomer.getEmail();
        String actualGender = actualCustomer.getGender();
        Long actualId = actualCustomer.getId();
        String actualName = actualCustomer.getName();
        assertSame(addresses2, actualAddresses);
        assertEquals(addresses, actualAddresses);
        assertEquals("jane.doe@example.org", actualEmail);
        assertEquals("Gender", actualGender);
        assertEquals(1L, actualId.longValue());
        assertEquals("Name", actualName);
    }
}

