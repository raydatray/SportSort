package ca.mcgill.ecse321.sportsregistrationw24.dto.Registration;

import java.sql.Date;

public class RegistrationCO {
  private Integer courseOfferingId;
  private Integer paymentInfoId;
  private Integer pricePaid;
  private Date registrationDate;

  public RegistrationCO(Integer courseOfferingId, Integer paymentInfoId, Integer pricePaid, Date registrationDate) {
    this.courseOfferingId = courseOfferingId;
    this.paymentInfoId = paymentInfoId;
    this.pricePaid = pricePaid;
    this.registrationDate = registrationDate;
  }

  public Integer getCourseOfferingId() { return this.courseOfferingId; }
  public Integer getPaymentInfoId() { return this.paymentInfoId; }
  public Integer getPricePaid() { return this.pricePaid; }
  public Date getRegistrationDate() {
      return this.registrationDate;
  }
}
