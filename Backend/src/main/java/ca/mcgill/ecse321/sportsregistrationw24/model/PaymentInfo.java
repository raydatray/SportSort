package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.*;

@Entity
@Table(name = "paymentinfo")
public class PaymentInfo {
  @Id
  @GeneratedValue
  private Integer id;
  public enum PaymentType { Credit, Debit }
  private PaymentType paymentType;
  private String cardNumber;
  private Integer cvv;
  private Integer expirationYear;
  private Integer expirationMonth;
  @ManyToOne
  @JoinColumn(name = "customer_account_id")
  private CustomerAccount customerAccount;

  public PaymentInfo() {}
  public PaymentInfo(PaymentType aPaymentType, String aCardNumber, Integer aCvv, Integer aExpirationYear, Integer aExpirationMonth, CustomerAccount aCustomerAccount) {
    this.paymentType = aPaymentType;
    this.cardNumber = aCardNumber;
    this.cvv = aCvv;
    this.expirationYear = aExpirationYear;
    this.expirationMonth = aExpirationMonth;
    this.customerAccount = aCustomerAccount;
  }

  public void setPaymentType(PaymentType aPaymentType) { this.paymentType = aPaymentType; }
  public void setCardNumber(String aCardNumber) { this.cardNumber = aCardNumber; }
  public void setCvv(Integer aCvv) { this.cvv = aCvv; }
  public void setExpirationYear(Integer aExpirationYear) { this.expirationYear = aExpirationYear; }
  public void setExpirationMonth(Integer aExpirationMonth) { this.expirationMonth = aExpirationMonth; }
  public void setCustomerAccount(CustomerAccount aCustomerAccount) { this.customerAccount = aCustomerAccount; }

  public Integer getId() { return this.id; }
  public PaymentType getPaymentType() { return this.paymentType; }
  public String getCardNumber() { return this.cardNumber; }
  public Integer getTrailingFourDigits() { return Integer.parseInt(this.cardNumber.substring(this.cardNumber.length() - 4)); }
  public Integer getCvv() { return this.cvv; }
  public Integer getExpirationYear() { return this.expirationYear; }
  public Integer getExpirationMonth() { return this.expirationMonth; }
  public CustomerAccount getCustomerAccount() { return this.customerAccount; }
}