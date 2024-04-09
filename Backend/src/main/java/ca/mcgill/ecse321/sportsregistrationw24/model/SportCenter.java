package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Time;

@Entity
@Table(name = "sportcenter")
public class SportCenter {
  @Id
  private String name;
  private String address;
  private String phoneNumber;
  private Time openingHour;
  private Time closingHour;

  public SportCenter() {}
  public SportCenter(String aName, String anAddress, String aPhoneNumber, Time aOpeningHour, Time aClosingHour) {
    this.name = aName;
    this.address = anAddress;
    this.phoneNumber = aPhoneNumber;
    this.openingHour = aOpeningHour;
    this.closingHour = aClosingHour;
  }

  public void setName(String aName) { this.name = aName; }
  public void setAddress(String anAddress) { this.address = anAddress; }
  public void setPhoneNumber(String aPhoneNumber) { this.phoneNumber = aPhoneNumber; }
  public void setOpeningHour(Time aOpeningHour) { this.openingHour = aOpeningHour; }
  public void setClosingHour(Time aClosingHour) { this.closingHour = aClosingHour; }

  public String getName() { return this.name; }
  public String getAddress() { return this.address; }
  public String getPhoneNumber() { return this.phoneNumber; }
  public Time getOpeningHour() { return this.openingHour; }
  public Time getClosingHour() { return this.closingHour; }
}