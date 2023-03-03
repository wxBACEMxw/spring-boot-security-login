package com.spring.security.login.controllers;

import com.spring.security.login.models.*;
import com.spring.security.login.payload.request.BankAccountRequest;
import com.spring.security.login.repository.*;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "false")
@RestController
@RequestMapping("/api/bankAccount")
public class BankAccountController {

    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    IBANGenerator ibanGenerator;
    @Autowired
    FinancialInstitutionRepository financialInstitutionRepository;


    public static final Logger logger = LoggerFactory.getLogger(BankAccountController.class);

    @PostMapping("/addBankAccount")
    public ResponseEntity<?> AddUserBankAccount(@Valid @RequestBody BankAccountRequest bankAccountRequest){
        BankAccount bankAccount = new BankAccount(
                bankAccountRequest.getAccountNumber(),
                bankAccountRequest.getName(),
                bankAccountRequest.getCountryCode(),
                bankAccountRequest.getCreationTime()
        );

        // set currencies

        Set<String> strCurrencies = bankAccountRequest.getCurrencies();
        Set<Currency> currencies = new HashSet<>();
        strCurrencies.forEach(currency ->{
            Currency currency1 = currencyRepository.findByName(currency)
                    .orElseThrow(()-> new RuntimeException("Currency not found !!!"));
            currencies.add(currency1);
        });
        bankAccount.setCurrency(currencies);

        // set User

        String userNameRef = bankAccountRequest.getUserName();
        User user = userRepository.findByUsername(userNameRef)
                        .orElseThrow(()-> new RuntimeException("user not found !!!"));
        bankAccount.setUser(user);

        // set Financial institution

        String bankNameRef = bankAccountRequest.getBankName();
        FinancialInstitution financialInstitution = financialInstitutionRepository.findByName(bankNameRef)
                .orElseThrow(()->new RuntimeException("Financial institution not found !!!"));
        bankAccount.setFinancialInstitution(financialInstitution);

        // set IBAN

        String iban;
        try {
            iban = ibanGenerator.generateIban(bankAccountRequest.getCountryCode(),financialInstitution.getBankCode(),bankAccountRequest.getAccountNumber());
        } catch (CheckDigitException e) {
            throw new RuntimeException(e);
        }
        bankAccount.setIBAN(iban);

        // save Bank Account

        bankAccountRepository.save(bankAccount);

        return ResponseEntity.ok().body(
                bankAccount
        );
    }
}
