package ca.mcgill.ecse321.sportsregistrationw24.dto.Registration;

import java.sql.Date;

public class RegistrationCO {
  private final Integer courseOfferingId;
  private final Integer paymentInfoId;
  private final Integer pricePaid;
  private final Date registrationDate;

  public RegistrationCO(Integer courseOfferingId, Integer paymentInfoId, Integer pricePaid, Date registrationDate) {
    this.courseOfferingId = courseOfferingId;
    this.paymentInfoId = paymentInfoId;
    this.pricePaid = pricePaid;
    this.registrationDate = registrationDate;
  }

  public Integer getCourseOfferingId() {
    return this.courseOfferingId;
  }

  public Integer getPaymentInfoId() {
    return this.paymentInfoId;
  }

  public Integer getPricePaid() {
    return this.pricePaid;
  }

  public Date getRegistrationDate() {
    return this.registrationDate;
  }
}
