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
  private int cardNumber;
  private int cvv;
  private int expirationYear;
  private int expirationMonth;

  @ManyToOne
  @JoinColumn(name = "customer_account_id")
  private CustomerAccount customerAccount;

  public PaymentInfo(PaymentType aPaymentType, Integer aCardNumber, Integer aCvv, Integer aExpirationYear, Integer aExpirationMonth, CustomerAccount aCustomerAccount) {
    this.paymentType = aPaymentType;
    this.cardNumber = aCardNumber;
    this.cvv = aCvv;
    this.expirationYear = aExpirationYear;
    this.expirationMonth = aExpirationMonth;
    if (!setCustomerAccount(aCustomerAccount)) {
      throw new RuntimeException("Unable to create PaymentInfo due to aCustomerAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public PaymentInfo() {}

  public void setPaymentType(PaymentType aPaymentType) { this.paymentType = aPaymentType; }

  public void setCardNumber(Integer aCardNumber) { this.cardNumber = aCardNumber; }

  public void setCvv(Integer aCvv) { this.cvv = aCvv; }

  public void setExpirationYear(Integer aExpirationYear) { this.expirationYear = aExpirationYear; }

  public void setExpirationMonth(Integer aExpirationMonth) { this.expirationMonth = aExpirationMonth; }

  public boolean setCustomerAccount(CustomerAccount aNewCustomerAccount) {
    boolean wasSet = false;
    if (aNewCustomerAccount != null) {
      customerAccount = aNewCustomerAccount;
      wasSet = true;
    }
    return wasSet;
  }

  public Integer getId() { return this.id; }

  public PaymentType getPaymentType() { return this.paymentType; }

  public Integer getCardNumber() { return this.cardNumber; }

  public Integer getCvv() { return this.cvv; }

  public Integer getExpirationYear() { return this.expirationYear; }

  public Integer getExpirationMonth() { return this.expirationMonth; }

  public CustomerAccount getCustomerAccount() { return this.customerAccount; }

}