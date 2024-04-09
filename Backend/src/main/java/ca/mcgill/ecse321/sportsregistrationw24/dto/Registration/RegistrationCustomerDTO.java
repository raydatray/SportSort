package ca.mcgill.ecse321.sportsregistrationw24.dto.Registration;

import ca.mcgill.ecse321.sportsregistrationw24.model.Registration;

import java.sql.Date;

public class RegistrationCustomerDTO {
  private Date registrationDate;
  private Integer pricePaid;
  private Integer courseOfferingId;
  private Integer paymentInfo;

  public RegistrationCustomerDTO() {}

  public RegistrationCustomerDTO(Date registrationDate, Integer pricePaid, Integer courseOfferingId, Integer paymentInfo) {
    this.registrationDate = registrationDate;
    this.pricePaid = pricePaid;
    this.courseOfferingId = courseOfferingId;
    this.paymentInfo = paymentInfo;
  }

  public RegistrationCustomerDTO(Registration registration) {
    this(registration.getRegisteredDate(), registration.getPricePaid(), registration.getCourseOffering().getId(), registration.getPaymentInfo().getTrailingFourDigits());
  }

  public Date getRegistrationDate() { return this.registrationDate; }
  public Integer getPricePaid() { return this.pricePaid; }
  public Integer getCourseOfferingId() { return this.courseOfferingId; }
  public Integer getPaymentInfo() { return this.paymentInfo; }

}
