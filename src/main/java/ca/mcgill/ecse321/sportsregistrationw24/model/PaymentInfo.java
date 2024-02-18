package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 69 "SportsCenter.ump"
public class PaymentInfo
{
  public PaymentInfo() {

  }

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PaymentType { Credit, Debit }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PaymentInfo Attributes
  private Integer id;
  private PaymentType paymentType;
  private Integer cardNumber;
  private Integer cvv;
  private Integer expirationYear;
  private Integer expirationMonth;

  //PaymentInfo Associations
  private CustomerAccount customerAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PaymentInfo(Integer aId, PaymentType aPaymentType, Integer aCardNumber, Integer aCvv, Integer aExpirationYear, Integer aExpirationMonth, CustomerAccount aCustomerAccount)
  {
    id = aId;
    paymentType = aPaymentType;
    cardNumber = aCardNumber;
    cvv = aCvv;
    expirationYear = aExpirationYear;
    expirationMonth = aExpirationMonth;
    if (!setCustomerAccount(aCustomerAccount))
    {
      throw new RuntimeException("Unable to create PaymentInfo due to aCustomerAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(Integer aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setPaymentType(PaymentType aPaymentType)
  {
    boolean wasSet = false;
    paymentType = aPaymentType;
    wasSet = true;
    return wasSet;
  }

  public boolean setCardNumber(Integer aCardNumber)
  {
    boolean wasSet = false;
    cardNumber = aCardNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setCvv(Integer aCvv)
  {
    boolean wasSet = false;
    cvv = aCvv;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpirationYear(Integer aExpirationYear)
  {
    boolean wasSet = false;
    expirationYear = aExpirationYear;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpirationMonth(Integer aExpirationMonth)
  {
    boolean wasSet = false;
    expirationMonth = aExpirationMonth;
    wasSet = true;
    return wasSet;
  }

  public Integer getId()
  {
    return id;
  }

  public PaymentType getPaymentType()
  {
    return paymentType;
  }

  public Integer getCardNumber()
  {
    return cardNumber;
  }

  public Integer getCvv()
  {
    return cvv;
  }

  public Integer getExpirationYear()
  {
    return expirationYear;
  }

  public Integer getExpirationMonth()
  {
    return expirationMonth;
  }
  /* Code from template association_GetOne */
  public CustomerAccount getCustomerAccount()
  {
    return customerAccount;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCustomerAccount(CustomerAccount aNewCustomerAccount)
  {
    boolean wasSet = false;
    if (aNewCustomerAccount != null)
    {
      customerAccount = aNewCustomerAccount;
      wasSet = true;
    }
    return wasSet;
  }

}