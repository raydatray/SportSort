package ca.mcgill.ecse321.sportsregistrationw24.dto;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.PaymentInfo;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentInfoDto {

  private Integer id;
  public enum PaymentType { Credit, Debit }
  private PaymentInfo.PaymentType paymentType;
  private Integer cardNumber;
  private Integer cvv;
  private Integer expirationYear;
  private Integer expirationMonth;
  private CustomerAccount customerAccount;


  private CustomerAccountRepository customerAccountRepository;

  public PaymentInfoDto() {}

  public PaymentInfoDto(Integer id, PaymentInfo.PaymentType paymentType, Integer cardNumber, Integer cvv, Integer expirationYear, Integer expirationMonth, String email) {
    this.id = id;
    this.paymentType = paymentType;
    this.cardNumber = cardNumber;
    this.cvv = cvv;
    this.expirationYear = expirationYear;
    this.expirationMonth = expirationMonth;

    CustomerAccount fetchedCustomer = customerAccountRepository.findByEmail(email).orElseGet(null);
    if (fetchedCustomer == null) {
      throw new IllegalArgumentException("CustomerAccount not found");
    }
    else {
      this.customerAccount = fetchedCustomer;
    }
  }

  public Integer getId() { return id; }
  public PaymentInfo.PaymentType getPaymentType() { return paymentType; }
  public Integer getCardNumber() { return cardNumber; }
  public Integer getCvv() { return cvv; }
  public Integer getExpirationYear() { return expirationYear; }
  public Integer getExpirationMonth() { return expirationMonth; }
  public String getCustomerAccountEmail() { return customerAccount.getEmail(); }
}
