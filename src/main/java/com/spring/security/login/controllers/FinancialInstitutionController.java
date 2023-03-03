package com.spring.security.login.controllers;

import com.spring.security.login.models.FinancialInstitution;
import com.spring.security.login.payload.request.FinancialInstitutionRequest;
import com.spring.security.login.repository.BankAccountRepository;
import com.spring.security.login.repository.CurrencyRepository;
import com.spring.security.login.repository.FinancialInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "false")
@RestController
@RequestMapping("/api/FinancialInstitution")
public class FinancialInstitutionController {
    @Autowired
    FinancialInstitutionRepository financialInstitutionRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    public ResponseEntity<?> addFinancialInstitution(@Valid @RequestBody FinancialInstitutionRequest financialInstitutionRequest){
        FinancialInstitution financialInstitution = new FinancialInstitution(
                financialInstitutionRequest.getBankCode(),
                financialInstitutionRequest.getName(),
                financialInstitutionRequest.getCity(),
                financialInstitutionRequest.getTown(),
                financialInstitutionRequest.getStreetName(),
                financialInstitutionRequest.getPostAddress()
                );

        // set Currencies

        // set Bank Accounts

        // save Financial institution

        return ResponseEntity.ok().body(
                "ok"
        );
    }

}
