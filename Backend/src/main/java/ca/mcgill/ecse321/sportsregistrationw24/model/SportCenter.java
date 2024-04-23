package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.HashMap;

@Entity
@Table(name = "sportcenter")
public class SportCenter {
  @Id
  private String name;
  private String address;
  private String phoneNumber;
  @ElementCollection
  @Enumerated(EnumType.STRING)
  @Fetch(FetchMode.JOIN)
  private HashMap<DayOfWeek, Time> operatingHours;

  public SportCenter() {}
  public SportCenter(String aName, String anAddress, String aPhoneNumber, HashMap<DayOfWeek, Time> someOperatingHours) {
    this.name = aName;
    this.address = anAddress;
    this.phoneNumber = aPhoneNumber;
    this.operatingHours = someOperatingHours;
  }

  public void setName(String aName) {
    this.name = aName;
  }
  public void setAddress(String anAddress) {
    this.address = anAddress;
  }
  public void setPhoneNumber(String aPhoneNumber) {
    this.phoneNumber = aPhoneNumber;
  }
  public void setOperatingHours(HashMap<DayOfWeek, Time> someOperatingHours) { this.operatingHours = someOperatingHours; }

  public String getName() {
    return this.name;
  }
  public String getAddress() {
    return this.address;
  }
  public String getPhoneNumber() {
    return this.phoneNumber;
  }
  public HashMap<DayOfWeek, Time> getOperatingHours() { return this.operatingHours; }
}