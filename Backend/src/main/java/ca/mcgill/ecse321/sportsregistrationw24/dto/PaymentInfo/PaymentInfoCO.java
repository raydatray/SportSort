package ca.mcgill.ecse321.sportsregistrationw24.dto.PaymentInfo;

import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;

public class PaymentInfoCO {
  private Integer id;
  private PaymentInfo.PaymentType paymentType;
  private String cardNumber;
  private Integer cvv;
  private Integer expirationYear;
  private Integer expirationMonth;

  public PaymentInfoCO() {
  }

  public PaymentInfoCO(Integer id, PaymentInfo.PaymentType paymentType, String cardNumber, Integer cvv, Integer expirationYear, Integer expirationMonth) {
    this.id = id;
    this.paymentType = paymentType;
    this.cardNumber = cardNumber;
    this.cvv = cvv;
    this.expirationYear = expirationYear;
    this.expirationMonth = expirationMonth;
  }

  public Integer getId() {
    return this.id;
  }

  public PaymentInfo.PaymentType getPaymentType() {
    return this.paymentType;
  }

  public String getCardNumber() {
    return this.cardNumber;
  }

  public Integer getCvv() {
    return this.cvv;
  }

  public Integer getExpirationYear() {
    return this.expirationYear;
  }

  public Integer getExpirationMonth() {
    return this.expirationMonth;
  }
}
