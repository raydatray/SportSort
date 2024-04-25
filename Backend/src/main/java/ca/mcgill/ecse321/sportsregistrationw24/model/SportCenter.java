package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
@Table(name = "sportcenter")
public class SportCenter {
  @Id
  @GeneratedValue
  private Integer id;
  private String name;
  private String address;
  private String phoneNumber;
  @ElementCollection
  @Enumerated(EnumType.STRING)
  @Fetch(FetchMode.JOIN)
  private HashMap<DayOfWeek, ArrayList<Time>> operatingHours;

  public SportCenter() {}
  public SportCenter(String aName, String anAddress, String aPhoneNumber, HashMap<DayOfWeek, ArrayList<Time>> someOperatingHours) {
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
  public void setOperatingHours(HashMap<DayOfWeek, ArrayList<Time>> someOperatingHours) { this.operatingHours = someOperatingHours; }

  public Integer getId() { return this.id; }
  public String getName() {
    return this.name;
  }
  public String getAddress() {
    return this.address;
  }
  public String getPhoneNumber() {
    return this.phoneNumber;
  }
  public HashMap<DayOfWeek, ArrayList<Time>> getOperatingHours() { return this.operatingHours; }
}