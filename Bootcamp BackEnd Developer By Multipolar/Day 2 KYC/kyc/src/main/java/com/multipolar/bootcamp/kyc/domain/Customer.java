package com.multipolar.bootcamp.kyc.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document
public class Customer implements Serializable {
    @Id
    private String id;
    @NotEmpty(message = "nik cannot be empty")
    private String nik;
    @NotEmpty(message = "first name cannot be empty")
    private String firstName;
    @NotEmpty(message = "Last Name cannot be empty")
    private String lastName;
    @NotEmpty(message = "email cannot be empty")
    private String email;
    @NotEmpty(message = "Phone Number cannot be empty")
    private String phoneNumber;
    @NotEmpty(message = "date of birth cannot be empty")
    @DateTimeFormat
    private LocalDate dateOfBirth;
    private MembershipStatus membershipStatus;
    private Address address;
}
