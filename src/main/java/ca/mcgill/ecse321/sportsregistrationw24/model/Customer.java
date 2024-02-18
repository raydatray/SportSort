package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CUSTOMER")
// line 34 "SportsCenter.ump"
public class Customer extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(Integer aId, String aEmail, String aPassword)
  {
    super(aId, aEmail, aPassword);
  }

}