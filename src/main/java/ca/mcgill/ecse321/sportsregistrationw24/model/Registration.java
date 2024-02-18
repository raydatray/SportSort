package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "registration")
// line 49 "SportsCenter.ump"
public class Registration
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Registration Attributes
  private Date date;

  //Registration Associations
  private Customer customer;
  private SportSession sportSession;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Registration(Date aDate, Customer aCustomer, SportSession aSportSession)
  {
    date = aDate;
    if (!setCustomer(aCustomer))
    {
      throw new RuntimeException("Unable to create Registration due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setSportSession(aSportSession))
    {
      throw new RuntimeException("Unable to create Registration due to aSportSession. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public SportSession getSportSession()
  {
    return sportSession;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCustomer(Customer aNewCustomer)
  {
    boolean wasSet = false;
    if (aNewCustomer != null)
    {
      customer = aNewCustomer;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSportSession(SportSession aNewSportSession)
  {
    boolean wasSet = false;
    if (aNewSportSession != null)
    {
      sportSession = aNewSportSession;
      wasSet = true;
    }
    return wasSet;
  }
}