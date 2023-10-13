package com.multipolar.bootcamp.account.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document
public class Account implements Serializable {
    @Id
    private String id;
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private AccountHolder accountHolder;
    private Double balance;
    private LocalDateTime openingDate = LocalDateTime.now();
    private LocalDateTime closingDate;
}
enum AccountType {
    SAVINGS,CHECKING,CERTIFICATE_OF_DEPOSIT
}
enum AccountStatus{
    OPEN,CLOSED,FROZEN,SPECIAL_STATUS
}
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
class AccountHolder implements Serializable{
    private String nik;
    private String name;
    private String address;
}
