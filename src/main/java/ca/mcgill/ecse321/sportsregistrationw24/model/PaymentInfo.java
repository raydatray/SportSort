package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 59 "SportsCenter.ump"
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
  private PaymentType paymentType;
  private int cardNumber;
  private int cvv;
  private int expirationYear;
  private int expirationMonth;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PaymentInfo(PaymentType aPaymentType, int aCardNumber, int aCvv, int aExpirationYear, int aExpirationMonth)
  {
    paymentType = aPaymentType;
    cardNumber = aCardNumber;
    cvv = aCvv;
    expirationYear = aExpirationYear;
    expirationMonth = aExpirationMonth;
  }

  //------------------------
  // INTERFACE
  //------------------------

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
}