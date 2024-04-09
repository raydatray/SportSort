package ca.mcgill.ecse321.sportsregistrationw24.dto.PaymentInfo;

import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;

public class PaymentInfoUpdateCO {
  private Integer id;
  private Integer expirationYear;
  private Integer expirationMonth;

  public PaymentInfoUpdateCO() {}
  public PaymentInfoUpdateCO(Integer id, Integer newExpirationYear, Integer newExpirationMonth) {
    this.id = id;
    this.expirationYear = newExpirationYear;
    this.expirationMonth = newExpirationMonth;
  }

  public Integer getId() { return this.id; }
  public Integer getExpirationYear() { return this.expirationYear; }
  public Integer getExpirationMonth() { return this.expirationMonth; }
}
