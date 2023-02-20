package com.spring.security.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.Date;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    @NotBlank
    private long paymentInformationIdentification;
    @NotBlank
    private String paymentMethod;
    @NotBlank
    private Date requestedExuctionDate;
    @NotBlank
    private Date creationDateTime;
    @NotBlank
    private String description;
    @NotBlank
    private String amount;
}
