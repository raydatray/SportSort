package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.sql.Date;
import java.sql.Time;

// line 53 "SportsCenter.ump"
public class CourseSession
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CourseSession Attributes
  private Integer id;
  private Date date;
  private Time startTime;
  private Time endTime;

  //CourseSession Associations
  private CourseOffering courseOffering;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CourseSession(Integer aId, Date aDate, Time aStartTime, Time aEndTime, CourseOffering aCourseOffering)
  {
    id = aId;
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;
    if (!setCourseOffering(aCourseOffering))
    {
      throw new RuntimeException("Unable to create CourseSession due to aCourseOffering. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public Integer getId()
  {
    return id;
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
  /* Code from template association_GetOne */
  public CourseOffering getCourseOffering()
  {
    return courseOffering;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCourseOffering(CourseOffering aNewCourseOffering)
  {
    boolean wasSet = false;
    if (aNewCourseOffering != null)
    {
      courseOffering = aNewCourseOffering;
      wasSet = true;
    }
    return wasSet;
  }

}