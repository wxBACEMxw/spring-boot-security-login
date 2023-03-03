package com.spring.security.login.repository;

import com.spring.security.login.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

   Optional<Currency>  findByName(String name);
}
