package com.spring.security.login.repository;

import com.spring.security.login.models.FinancialInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinancialInstitutionRepository extends JpaRepository {
    Optional<FinancialInstitution> findByName(String name);
}
