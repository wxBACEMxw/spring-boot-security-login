package com.spring.security.login.repository;

import com.spring.security.login.models.BankAccount;
import com.spring.security.login.models.FinancialInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByIBAN(String IBAN);
}
