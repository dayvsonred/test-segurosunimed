package com.example.api.DTO;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObjectOfError implements Serializable {

    private String error;
    private String code;
}
