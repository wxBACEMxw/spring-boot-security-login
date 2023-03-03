package com.spring.security.login.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FinancialInstitutionRequest {
    private String name;
    private String bankCode;
    private String city;
    private String town;
    private String streetName;
    private String postAddress;
}
