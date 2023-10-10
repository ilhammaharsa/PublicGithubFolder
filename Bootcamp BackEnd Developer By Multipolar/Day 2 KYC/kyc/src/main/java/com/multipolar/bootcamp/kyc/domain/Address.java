package com.multipolar.bootcamp.kyc.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document
public class Address implements Serializable {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
