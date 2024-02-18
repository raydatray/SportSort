package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "sportsession")
// line 38 "SportsCenter.ump"
public class SportSession
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum SessionType { Cardio, Stretching, StrengthTraining }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  @Id
  //SportSession Attributes
  private Integer id;
  private SessionType sessionType;
  private Date date;
  private Time startTime;
  private Time endTime;
  private Integer floorNumber;
  private Integer roomNumber;

  //SportSession Associations
  private SportClass sportClass;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SportSession(Integer aId, SessionType aSessionType, Date aDate, Time aStartTime, Time aEndTime, Integer aFloorNumber, Integer aRoomNumber, SportClass aSportClass)
  {
    id = aId;
    sessionType = aSessionType;
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;
    floorNumber = aFloorNumber;
    roomNumber = aRoomNumber;
    if (!setSportClass(aSportClass))
    {
      throw new RuntimeException("Unable to create SportSession due to aSportClass. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(Integer aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setSessionType(SessionType aSessionType)
  {
    boolean wasSet = false;
    sessionType = aSessionType;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setFloorNumber(Integer aFloorNumber)
  {
    boolean wasSet = false;
    floorNumber = aFloorNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setRoomNumber(Integer aRoomNumber)
  {
    boolean wasSet = false;
    roomNumber = aRoomNumber;
    wasSet = true;
    return wasSet;
  }

  public Integer getId()
  {
    return id;
  }

  public SessionType getSessionType()
  {
    return sessionType;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public Integer getFloorNumber()
  {
    return floorNumber;
  }

  public Integer getRoomNumber()
  {
    return roomNumber;
  }
  /* Code from template association_GetOne */
  public SportClass getSportClass()
  {
    return sportClass;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSportClass(SportClass aNewSportClass)
  {
    boolean wasSet = false;
    if (aNewSportClass != null)
    {
      sportClass = aNewSportClass;
      wasSet = true;
    }
    return wasSet;
  }
}