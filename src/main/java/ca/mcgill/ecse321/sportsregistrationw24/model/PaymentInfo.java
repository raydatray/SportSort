package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 64 "model.ump"
// line 140 "model.ump"
public class PaymentInfo
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PaymentType { Credit, Debit }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PaymentInfo Attributes
  private int id;
  private PaymentType paymentType;
  private int cardNumber;
  private int cvv;
  private int expirationYear;
  private int expirationMonth;

  //PaymentInfo Associations
  private Customer customer;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PaymentInfo(int aId, PaymentType aPaymentType, int aCardNumber, int aCvv, int aExpirationYear, int aExpirationMonth, Customer aCustomer)
  {
    id = aId;
    paymentType = aPaymentType;
    cardNumber = aCardNumber;
    cvv = aCvv;
    expirationYear = aExpirationYear;
    expirationMonth = aExpirationMonth;
    if (!setCustomer(aCustomer))
    {
      throw new RuntimeException("Unable to create PaymentInfo due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
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

  public boolean setCardNumber(int aCardNumber)
  {
    boolean wasSet = false;
    cardNumber = aCardNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setCvv(int aCvv)
  {
    boolean wasSet = false;
    cvv = aCvv;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpirationYear(int aExpirationYear)
  {
    boolean wasSet = false;
    expirationYear = aExpirationYear;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpirationMonth(int aExpirationMonth)
  {
    boolean wasSet = false;
    expirationMonth = aExpirationMonth;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public PaymentType getPaymentType()
  {
    return paymentType;
  }

  public int getCardNumber()
  {
    return cardNumber;
  }

  public int getCvv()
  {
    return cvv;
  }

  public int getExpirationYear()
  {
    return expirationYear;
  }

  public int getExpirationMonth()
  {
    return expirationMonth;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCustomer(Customer aNewCustomer)
  {
    boolean wasSet = false;
    if (aNewCustomer != null)
    {
      customer = aNewCustomer;
      wasSet = true;
    }
    return wasSet;
  }
}