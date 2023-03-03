package com.spring.security.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FinancialInstitution")
public class FinancialInstitution {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String BICFI;
    private String bankCode;
    private String name;
    private String city;
    private String town;
    private String streetAddress;
    private String postAddress;
    @OneToMany
    @JoinColumn(name = "FinancialInstitution_id")
    private Set<BankAccount> bankAccounts;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "financialInstitution_currencies",
    joinColumns = @JoinColumn(name= "FinancialInstitution_id"),
    inverseJoinColumns = @JoinColumn(name ="currency_id"))
    private Set<Currency> currency=  new HashSet<>();

    public FinancialInstitution(String bankCode, String name, String city, String town, String streetAddress, String postAddress) {
        this.bankCode = bankCode;
        this.name = name;
        this.city = city;
        this.town = town;
        this.streetAddress = streetAddress;
        this.postAddress = postAddress;
    }
}
