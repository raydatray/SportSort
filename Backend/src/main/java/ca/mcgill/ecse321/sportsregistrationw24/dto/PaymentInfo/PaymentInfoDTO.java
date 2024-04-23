package ca.mcgill.ecse321.sportsregistrationw24.dto.PaymentInfo;

import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;

public class PaymentInfoDTO {
  private Integer id;
  private PaymentInfo.PaymentType paymentType;
  private Integer cardNumber;
  private Integer expirationYear;
  private Integer expirationMonth;

  public PaymentInfoDTO() {
  }

  public PaymentInfoDTO(Integer aId, PaymentInfo.PaymentType aPaymentType, Integer aCardNumber, Integer aExpirationYear, Integer aExpirationMonth, Integer aCustomerAccountId) {
    this.id = aId;
    this.paymentType = aPaymentType;
    this.cardNumber = aCardNumber;
    this.expirationYear = aExpirationYear;
    this.expirationMonth = aExpirationMonth;
  }

  public PaymentInfoDTO(PaymentInfo aPaymentInfo) {
    this(aPaymentInfo.getId(), aPaymentInfo.getPaymentType(), aPaymentInfo.getTrailingFourDigits(), aPaymentInfo.getExpirationYear(), aPaymentInfo.getExpirationMonth(), aPaymentInfo.getCustomerAccount().getId());
  }

  public Integer getId() {
    return this.id;
  }

  public PaymentInfo.PaymentType getPaymentType() {
    return this.paymentType;
  }

  public Integer getCardNumber() {
    return this.cardNumber;
  }

  public Integer getExpirationYear() {
    return this.expirationYear;
  }

  public Integer getExpirationMonth() {
    return this.expirationMonth;
  }
}
