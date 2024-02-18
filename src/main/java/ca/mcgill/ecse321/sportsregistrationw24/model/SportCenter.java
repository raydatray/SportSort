package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.sql.Time;

@Entity
@Table(name = "sportcenter")
// line 1 "SportsCenter.ump"
public class SportCenter
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SportCenter Attributes
  private String name;
  private Time openingHour;
  private Time closingHour;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SportCenter(String aName, Time aOpeningHour, Time aClosingHour)
  {
    name = aName;
    openingHour = aOpeningHour;
    closingHour = aClosingHour;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setOpeningHour(Time aOpeningHour)
  {
    boolean wasSet = false;
    openingHour = aOpeningHour;
    wasSet = true;
    return wasSet;
  }

  public boolean setClosingHour(Time aClosingHour)
  {
    boolean wasSet = false;
    closingHour = aClosingHour;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public Time getOpeningHour()
  {
    return openingHour;
  }

  public Time getClosingHour()
  {
    return closingHour;
  }

}