package com.example.api.DTO;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String gender;
    private List<Address> addresses;

    public static CustomerDto fromEntity(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setGender(customer.getGender());
        customerDto.setAddresses(customer.getAddresses());
        return customerDto;
    }
}
