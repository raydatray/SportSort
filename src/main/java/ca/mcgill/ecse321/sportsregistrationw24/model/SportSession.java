package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.sql.Date;
import java.sql.Time;

// line 42 "SportsCenter.ump"
public class SportSession
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum SessionType { Cardio, Stretching, StrengthTraining }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SportSession Attributes
  private SessionType sessionType;
  private Date date;
  private Time startTime;
  private Time endTime;
  private Integer floorNumber;
  private Integer roomNumber;

  //SportSession Associations
  private SportCenter sportCenter;
  private SportClass sportClass;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SportSession(SessionType aSessionType, Date aDate, Time aStartTime, Time aEndTime, Integer aFloorNumber, Integer aRoomNumber, SportCenter aSportCenter, SportClass aSportClass)
  {
    sessionType = aSessionType;
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;
    floorNumber = aFloorNumber;
    roomNumber = aRoomNumber;
    boolean didAddSportCenter = setSportCenter(aSportCenter);
    if (!didAddSportCenter)
    {
      throw new RuntimeException("Unable to create sportSession due to sportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setSportClass(aSportClass))
    {
      throw new RuntimeException("Unable to create SportSession due to aSportClass. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  public SportCenter getSportCenter()
  {
    return sportCenter;
  }
  /* Code from template association_GetOne */
  public SportClass getSportClass()
  {
    return sportClass;
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
      existingSportCenter.removeSportSession(this);
    }
    sportCenter.addSportSession(this);
    wasSet = true;
    return wasSet;
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