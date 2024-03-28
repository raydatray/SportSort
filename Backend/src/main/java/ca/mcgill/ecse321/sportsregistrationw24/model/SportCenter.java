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
  private Time openingHour;
  private Time closingHour;

  public SportCenter(String aName, Time aOpeningHour, Time aClosingHour) {
    this.name = aName;
    this.openingHour = aOpeningHour;
    this.closingHour = aClosingHour;
  }

  public SportCenter() {}

  public void setName(String aName) { this.name = aName; }

  public void setOpeningHour(Time aOpeningHour) { this.openingHour = aOpeningHour; }

  public void setClosingHour(Time aClosingHour) { this.closingHour = aClosingHour; }

  public String getName() { return this.name; }

  public Time getOpeningHour() { return this.openingHour; }

  public Time getClosingHour() { return this.closingHour; }

}