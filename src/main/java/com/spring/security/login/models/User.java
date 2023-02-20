package com.spring.security.login.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;
  @NotBlank
  private String fullName;

  @NotBlank
  private String city;
  @NotBlank
  private String town;
  @NotBlank
  private String streetName;
  @NotBlank
  private String postAddress;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", 
             joinColumns = @JoinColumn(name = "user_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
  @OneToOne(cascade = CascadeType.ALL)
 @JoinTable(name = "user_bankAccount",
 joinColumns ={
         @JoinColumn(name = "user_id", referencedColumnName = "id")
 }, inverseJoinColumns = {
           @JoinColumn(name = "bankAccount_id", referencedColumnName = "id")
         })
  private BankAccount bankAccount;

  public User() {
  }


  public User(String username, String email, String password, String fullName, String city, String town, String streetName, String postAddress) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.fullName = fullName;
    this.city = city;
    this.town = town;
    this.streetName = streetName;
    this.postAddress = postAddress;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getTown() {
    return town;
  }

  public void setTown(String town) {
    this.town = town;
  }

  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public String getPostAddress() {
    return postAddress;
  }

  public void setPostAddress(String postAddress) {
    this.postAddress = postAddress;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}
