package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.sql.Date;

// line 53 "SportsCenter.ump"
public class Registration
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Registration Attributes
  private Date date;

  //Registration Associations
  private Customer customer;
  private SpecificSession specificSession;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Registration(Date aDate, Customer aCustomer, SpecificSession aSpecificSession)
  {
    date = aDate;
    if (!setCustomer(aCustomer))
    {
      throw new RuntimeException("Unable to create Registration due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setSpecificSession(aSpecificSession))
    {
      throw new RuntimeException("Unable to create Registration due to aSpecificSession. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  public SpecificSession getSpecificSession()
  {
    return specificSession;
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
  public boolean setSpecificSession(SpecificSession aNewSpecificSession)
  {
    boolean wasSet = false;
    if (aNewSpecificSession != null)
    {
      specificSession = aNewSpecificSession;
      wasSet = true;
    }
    return wasSet;
  }
}