package com.spring.security.login.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bankAccount")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String accountNumber;
    @NotBlank
    private String  name;
    @NotBlank
    private String countryCode ;
    @NotBlank
    private Date creationTime;
    private String IBAN;
    @ManyToOne
    @JoinColumn(name = "FinancialInstitution_id", insertable = false, updatable = false)
    private FinancialInstitution financialInstitution;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "BankAccount_currencies",
    joinColumns = @JoinColumn(name = "currency_id"), inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<Currency> currency=  new HashSet<>();
    @OneToOne(mappedBy = "bankAccount")
    private User user;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "BankAccount_transactions",
    joinColumns = @JoinColumn(name = "transaction_id"), inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<Transaction> transactions = new HashSet<>();

    public BankAccount(String accountNumber, String name, String countryCode, Date creationTime) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.countryCode = countryCode;
        this.creationTime = creationTime;
    }
}
