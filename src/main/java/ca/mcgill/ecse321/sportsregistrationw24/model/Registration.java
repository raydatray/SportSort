package ca.mcgill.ecse321.sportsregistrationw24.model;

import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "registration")
public class Registration {
  @EmbeddedId
  private RegistrationId id;
  private Date registeredDate;

  @ManyToOne
  @MapsId("customerAccountId")
  @JoinColumn(name = "customer_account_id")
  private CustomerAccount customerAccount;

  @ManyToOne
  @MapsId("courseOfferingId")
  @JoinColumn(name = "course_offering_id")
  private CourseOffering courseOffering;

  @ManyToOne
  @AssociationOverride(name = "PaymentInfoId",
      joinColumns = @JoinColumn(name = "payment_info_id"))
  private PaymentInfo paymentInfo;

  public Registration(Date aRegisteredDate, CourseOffering aCourseOffering, CustomerAccount aCustomerAccount, PaymentInfo aPaymentInfo) {
    this.registeredDate = aRegisteredDate;
    this.paymentInfo = aPaymentInfo;

    if (!setCourseOffering (aCourseOffering)) {
      throw new RuntimeException("Unable to create Registration due to aCourseOffering. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCustomerAccount (aCustomerAccount)) {
      throw new RuntimeException("Unable to create Registration due to aCustomerAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }

    this.id = new RegistrationId(courseOffering.getId(), customerAccount.getId());
  }

  public Registration() {}

  public void setRegisteredDate(Date aRegisteredDate) { this.registeredDate = aRegisteredDate; }

  public boolean setCourseOffering (CourseOffering aNewCourseOffering) {
    boolean wasSet = false;
    if (aNewCourseOffering != null) {
      courseOffering = aNewCourseOffering;
      wasSet = true;
    }
    return wasSet;
  }

  public boolean setCustomerAccount (CustomerAccount aNewCustomerAccount) {
    boolean wasSet = false;
    if (aNewCustomerAccount != null) {
      customerAccount = aNewCustomerAccount;
      wasSet = true;
    }
    return wasSet;
  }

  public RegistrationId getId() { return this.id; }

  public Date getRegisteredDate()
  {
    return this.registeredDate;
  }

  public CourseOffering getCourseOffering()
  {
    return this.courseOffering;
  }

  public CustomerAccount getCustomerAccount()
  {
    return this.customerAccount;
  }
  public PaymentInfo getPaymentInfo() { return this.paymentInfo; }

}