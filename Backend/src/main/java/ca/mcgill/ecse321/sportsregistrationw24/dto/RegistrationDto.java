package ca.mcgill.ecse321.sportsregistrationw24.dto;

import ca.mcgill.ecse321.sportsregistrationw24.model.Registration;
import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;

import java.sql.Date;

public class RegistrationDto {
  String courseTypeName;
  RegistrationId registrationId;
  String paymentInfoCardNumber;
  Integer registrationPrice;
  Integer paymentInfoID;
  Date registrationDate;

  public RegistrationDto(String courseTypeName, RegistrationId registrationId, String paymentInfoCardNumber, Integer registrationPrice, Date registrationDate) {
    this.courseTypeName = courseTypeName;
    this.registrationId = registrationId;

    //TODO switch to statement below when card number is switched to Long
    //this.paymentInfoCardNumber = hideCardNumber(paymentInfoCardNumber).toString();
    this.paymentInfoCardNumber = paymentInfoCardNumber;
    this.registrationPrice = registrationPrice;
    this.registrationDate = registrationDate;
  }
  /*
  public RegistrationDto(Integer courseOfferingId, Integer customerAccountId, Integer paymentInfoId, Date registrationDate) {
    this.courseTypeName = courseTypeName;
    this.registrationId = registrationId;

    //TODO switch to statement below when card number is switched to Long
    //this.paymentInfoCardNumber = hideCardNumber(paymentInfoCardNumber).toString();
    this.paymentInfoCardNumber = paymentInfoCardNumber;
    this.registrationPrice = registrationPrice;
    this.registrationDate = registrationDate;
  }
  */

  public RegistrationDto(Registration aRegistration) {
    this.registrationId = aRegistration.getId();
    this.paymentInfoID = aRegistration.getPaymentInfo().getId();
    // TODO switch to statement below when card number is switched to Long
    // this.paymentInfoCardNumber = hideCardNumber(aRegistration.getPaymentInfo().getCardNumber().toString()).toString();
    this.paymentInfoCardNumber = aRegistration.getPaymentInfo().getCardNumber().toString();
    this.registrationPrice = 100;
    this.courseTypeName = aRegistration.getCourseOffering().getCourseType().getCourseName();
    this.registrationDate = aRegistration.getRegisteredDate();
  }

  public String getCourseTypeName() { return this.courseTypeName; }
  public RegistrationId getRegistrationId() { return this.registrationId; }
  public String getPaymentInfoCardNumber() { return this.paymentInfoCardNumber; }
  public Integer getRegistrationPrice() { return this.registrationPrice; }
  public Date getRegistrationDate() { return this.registrationDate; }
  public Integer getPaymentInfoID() { return this.paymentInfoID; }
  private StringBuilder hideCardNumber(String paymentInfoCardNumber) {
    StringBuilder processedCardNumber = new StringBuilder("**** **** **** ");
    for (int i = 4; i > 0; i--) { // TODO - Change back to i = 11 i < 7
      processedCardNumber.append(paymentInfoCardNumber.charAt(i));
    }
    return processedCardNumber;
  }
}