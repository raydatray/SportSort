package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.sql.Date;

// line 47 "SportsCenter.ump"
public class Registration
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Registration Attributes
  private Date registeredDate;

  //Registration Associations
  private CourseOffering courseOffering;
  private CustomerAccount customerAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Registration(Date aRegisteredDate, CourseOffering aCourseOffering, CustomerAccount aCustomerAccount)
  {
    registeredDate = aRegisteredDate;
    if (!setCourseOffering(aCourseOffering))
    {
      throw new RuntimeException("Unable to create Registration due to aCourseOffering. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCustomerAccount(aCustomerAccount))
    {
      throw new RuntimeException("Unable to create Registration due to aCustomerAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRegisteredDate(Date aRegisteredDate)
  {
    boolean wasSet = false;
    registeredDate = aRegisteredDate;
    wasSet = true;
    return wasSet;
  }

  public Date getRegisteredDate()
  {
    return registeredDate;
  }
  /* Code from template association_GetOne */
  public CourseOffering getCourseOffering()
  {
    return courseOffering;
  }
  /* Code from template association_GetOne */
  public CustomerAccount getCustomerAccount()
  {
    return customerAccount;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCourseOffering(CourseOffering aNewCourseOffering)
  {
    boolean wasSet = false;
    if (aNewCourseOffering != null)
    {
      courseOffering = aNewCourseOffering;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCustomerAccount(CustomerAccount aNewCustomerAccount)
  {
    boolean wasSet = false;
    if (aNewCustomerAccount != null)
    {
      customerAccount = aNewCustomerAccount;
      wasSet = true;
    }
    return wasSet;
  }

}