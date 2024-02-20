package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import jakarta.persistence.*;

@Entity
@Table(name = "userAccounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE")
// line 7 "SportsCenter.ump"
public abstract class UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  @Id
  //UserAccount Attributes
  private Integer id;
  private String email;
  private String password;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserAccount(Integer aId, String aEmail, String aPassword)
  {
    id = aId;
    email = aEmail;
    password = aPassword;
  }

  public UserAccount() {
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(Integer aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public Integer getId()
  {
    return id;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }
}