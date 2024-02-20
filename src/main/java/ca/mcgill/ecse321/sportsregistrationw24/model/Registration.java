package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import jakarta.persistence.*;

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
  private Date registeredDate;

  //Registration Associations
  @EmbeddedId
  private RegistrationId id;

  @ManyToOne
  @MapsId("customerAccountId")
  @JoinColumn(name = "customer_account_id")
  private CustomerAccount customerAccount;


  @ManyToOne
  @MapsId("courseOfferingId")
  @JoinColumn(name = "course_offering_id")
  private CourseOffering courseOffering;

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

    id = new RegistrationId(courseOffering.getId(), customerAccount.getId());
  }

  public Registration() {

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

  public RegistrationId getId() { return id;  }
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