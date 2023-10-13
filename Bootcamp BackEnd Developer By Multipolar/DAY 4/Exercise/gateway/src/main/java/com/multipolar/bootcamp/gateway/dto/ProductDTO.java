package com.multipolar.bootcamp.gateway.dto;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductDTO implements Serializable {
    @Id
    private String id;
    private String productName;
    private ProductType productType;
    private Double interestRate;
    private Double minimumBalance;
    private Double maximumLoanAmount;
    private String termsAndConditions;
    private LocalDateTime dateOfCreation =LocalDateTime.now();
}
enum ProductType{
    SAVINGS_ACCOUNT,
    CHECKING_ACCOUNT,
    LOAN
}
