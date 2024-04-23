package ca.mcgill.ecse321.sportsregistrationw24.dto;

import ca.mcgill.ecse321.sportsregistrationw24.model.SportCenter;

import java.sql.Time;

public class SportsCenterDTO {
  private String name;
  private String address;
  private String phoneNumber;
  private Time openingHour;
  private Time closingHour;

  public SportsCenterDTO() {
  }

  public SportsCenterDTO(String aName, String anAddress, String aPhoneNumber, Time aOpeningHour, Time aClosingHour) {
    this.name = aName;
    this.address = anAddress;
    this.phoneNumber = aPhoneNumber;
    this.openingHour = aOpeningHour;
    this.closingHour = aClosingHour;
  }

  public SportsCenterDTO(SportCenter aSportsCenter) {
    this(aSportsCenter.getName(), aSportsCenter.getAddress(), aSportsCenter.getPhoneNumber(), aSportsCenter.getOpeningHour(), aSportsCenter.getClosingHour());
  }

  public String getName() {
    return this.name;
  }

  public String getAddress() {
    return this.address;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public Time getOpeningHour() {
    return this.openingHour;
  }

  public Time getClosingHour() {
    return this.closingHour;
  }
}
