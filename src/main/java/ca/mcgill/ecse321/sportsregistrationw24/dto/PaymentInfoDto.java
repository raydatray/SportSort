package ca.mcgill.ecse321.sportsregistrationw24.dto;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentInfoDto {

  private Integer id;
  //public enum PaymentType { Credit, Debit }
  private PaymentInfo.PaymentType paymentType;
  private Integer cardNumber;
  private Integer cvv;
  private Integer expirationYear;
  private Integer expirationMonth;



  public PaymentInfoDto() {}

  public PaymentInfoDto(Integer id, PaymentInfo.PaymentType paymentType, Integer cardNumber, Integer cvv, Integer expirationYear, Integer expirationMonth) {
    this.id = id;
    this.paymentType = paymentType;
    this.cardNumber = cardNumber;
    this.cvv = cvv;
    this.expirationYear = expirationYear;
    this.expirationMonth = expirationMonth;
  }

  public Integer getId() { return this.id; }
  public PaymentInfo.PaymentType getPaymentType() { return this.paymentType; }
  public Integer getCardNumber() { return this.cardNumber; }
  public Integer getCvv() { return this.cvv; }
  public Integer getExpirationYear() { return this.expirationYear; }
  public Integer getExpirationMonth() { return this.expirationMonth; }

}
