package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.util.*;

// line 39 "SportsCenter.ump"
public class Customer extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Associations
  private SportCenter sportCenter;
  private List<PaymentInfo> paymentInfos;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(int aId, String aEmail, String aPassword, SportCenter aSportCenter)
  {
    super(aId, aEmail, aPassword);
    boolean didAddSportCenter = setSportCenter(aSportCenter);
    if (!didAddSportCenter)
    {
      throw new RuntimeException("Unable to create customer due to sportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    paymentInfos = new ArrayList<PaymentInfo>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public SportCenter getSportCenter()
  {
    return sportCenter;
  }
  /* Code from template association_GetMany */
  public PaymentInfo getPaymentInfo(int index)
  {
    PaymentInfo aPaymentInfo = paymentInfos.get(index);
    return aPaymentInfo;
  }

  public List<PaymentInfo> getPaymentInfos()
  {
    List<PaymentInfo> newPaymentInfos = Collections.unmodifiableList(paymentInfos);
    return newPaymentInfos;
  }

  public int numberOfPaymentInfos()
  {
    int number = paymentInfos.size();
    return number;
  }

  public boolean hasPaymentInfos()
  {
    boolean has = paymentInfos.size() > 0;
    return has;
  }

  public int indexOfPaymentInfo(PaymentInfo aPaymentInfo)
  {
    int index = paymentInfos.indexOf(aPaymentInfo);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setSportCenter(SportCenter aSportCenter)
  {
    boolean wasSet = false;
    if (aSportCenter == null)
    {
      return wasSet;
    }

    SportCenter existingSportCenter = sportCenter;
    sportCenter = aSportCenter;
    if (existingSportCenter != null && !existingSportCenter.equals(aSportCenter))
    {
      existingSportCenter.removeCustomer(this);
    }
    sportCenter.addCustomer(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPaymentInfos()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addPaymentInfo(PaymentInfo aPaymentInfo)
  {
    boolean wasAdded = false;
    if (paymentInfos.contains(aPaymentInfo)) { return false; }
    paymentInfos.add(aPaymentInfo);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePaymentInfo(PaymentInfo aPaymentInfo)
  {
    boolean wasRemoved = false;
    if (paymentInfos.contains(aPaymentInfo))
    {
      paymentInfos.remove(aPaymentInfo);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPaymentInfoAt(PaymentInfo aPaymentInfo, int index)
  {  
    boolean wasAdded = false;
    if(addPaymentInfo(aPaymentInfo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPaymentInfos()) { index = numberOfPaymentInfos() - 1; }
      paymentInfos.remove(aPaymentInfo);
      paymentInfos.add(index, aPaymentInfo);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePaymentInfoAt(PaymentInfo aPaymentInfo, int index)
  {
    boolean wasAdded = false;
    if(paymentInfos.contains(aPaymentInfo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPaymentInfos()) { index = numberOfPaymentInfos() - 1; }
      paymentInfos.remove(aPaymentInfo);
      paymentInfos.add(index, aPaymentInfo);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPaymentInfoAt(aPaymentInfo, index);
    }
    return wasAdded;
  }
}