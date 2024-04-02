package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.*;

@Entity
@Table(name = "userAccounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE")
public abstract class UserAccount {
  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  private String email;
  private String password;
  private String token;

  public UserAccount() {}
  public UserAccount(String aName, String aEmail, String aPassword) {
    this.name = aName;
    this.email = aEmail;
    this.password = aPassword;
  }

  public void setId (Integer id) { this.id = id;}
  public void setName(String name) { this.name = name; }
  public void setEmail(String email) { this.email = email; }
  public void setPassword(String password) { this.password = password; }
  public void setToken(String token) { this.token = token; }

  public Integer getId()
  {
    return this.id;
  }
  public String getName() { return this.name; }
  public String getEmail()
  {
    return this.email;
  }
  public String getPassword()
  {
    return this.password;
  }
  public String getToken() { return this.token; }
  @Transient
  public String getUserType() { return this.getClass().getAnnotation(DiscriminatorValue.class).value(); }
}