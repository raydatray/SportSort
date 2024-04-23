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
  private Integer pricePaid;
  @ManyToOne
  @MapsId("customerAccountId")
  @JoinColumn(name = "customer_account_id")
  private CustomerAccount customerAccount;
  @ManyToOne
  @MapsId("courseOfferingId")
  @JoinColumn(name = "course_offering_id")
  private CourseOffering courseOffering;
  @ManyToOne
  @AssociationOverride(name = "PaymentInfoId", joinColumns = @JoinColumn(name = "payment_info_id"))
  private PaymentInfo paymentInfo;

  public Registration() {}
  public Registration(Date aRegisteredDate, Integer aPricePaid, CourseOffering aCourseOffering, CustomerAccount aCustomerAccount, PaymentInfo aPaymentInfo) {
    this.registeredDate = aRegisteredDate;
    this.pricePaid = aPricePaid;
    this.courseOffering = aCourseOffering;
    this.customerAccount = aCustomerAccount;
    this.paymentInfo = aPaymentInfo;
    this.id = new RegistrationId(courseOffering.getId(), customerAccount.getId());
  }

  public void setRegisteredDate(Date aRegisteredDate) {
    this.registeredDate = aRegisteredDate;
  }
  public void setPricePaid(Integer aPricePaid) {
    this.pricePaid = aPricePaid;
  }
  public void setCourseOffering(CourseOffering aNewCourseOffering) {
    this.courseOffering = aNewCourseOffering;
  }
  public void setCustomerAccount(CustomerAccount aNewCustomerAccount) {
    this.customerAccount = aNewCustomerAccount;
  }
  public void setPaymentInfo(PaymentInfo aNewPaymentInfo) {
    this.paymentInfo = aNewPaymentInfo;
  }

  public RegistrationId getId() {
    return this.id;
  }
  public Date getRegisteredDate() {
    return this.registeredDate;
  }
  public Integer getPricePaid() {
    return this.pricePaid;
  }
  public CourseOffering getCourseOffering() {
    return this.courseOffering;
  }
  public CustomerAccount getCustomerAccount() {
    return this.customerAccount;
  }
  public PaymentInfo getPaymentInfo() {
    return this.paymentInfo;
  }
}