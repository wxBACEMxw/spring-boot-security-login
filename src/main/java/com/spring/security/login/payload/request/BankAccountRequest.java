package com.spring.security.login.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankAccountRequest {
    @NotBlank
    private String bankName;
    @NotBlank
    private String accountNumber;
    @NotBlank
    private String countryCode;
    @NotBlank
    private String name;
    @NotBlank
    private Date CreationTime;
    @NotBlank
    private String userName;
    private Set<String> currencies;

}
