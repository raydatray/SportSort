package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.sql.Date;

// line 38 "SportsCenter.ump"
public class CourseOffering
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CourseOffering Attributes
  private Integer id;
  private Date startDate;
  private Date endDate;

  //CourseOffering Associations
  private Room room;
  private CourseType courseType;
  private InstructorAccount instructorAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CourseOffering(Integer aId, Date aStartDate, Date aEndDate, Room aRoom, CourseType aCourseType, InstructorAccount aInstructorAccount)
  {
    id = aId;
    startDate = aStartDate;
    endDate = aEndDate;
    if (!setRoom(aRoom))
    {
      throw new RuntimeException("Unable to create CourseOffering due to aRoom. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCourseType(aCourseType))
    {
      throw new RuntimeException("Unable to create CourseOffering due to aCourseType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setInstructorAccount(aInstructorAccount))
    {
      throw new RuntimeException("Unable to create CourseOffering due to aInstructorAccount. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
    wasSet = true;
    return wasSet;
  }

  public Integer getId()
  {
    return id;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }
  /* Code from template association_GetOne */
  public Room getRoom()
  {
    return room;
  }
  /* Code from template association_GetOne */
  public CourseType getCourseType()
  {
    return courseType;
  }
  /* Code from template association_GetOne */
  public InstructorAccount getInstructorAccount()
  {
    return instructorAccount;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setRoom(Room aNewRoom)
  {
    boolean wasSet = false;
    if (aNewRoom != null)
    {
      room = aNewRoom;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCourseType(CourseType aNewCourseType)
  {
    boolean wasSet = false;
    if (aNewCourseType != null)
    {
      courseType = aNewCourseType;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setInstructorAccount(InstructorAccount aNewInstructorAccount)
  {
    boolean wasSet = false;
    if (aNewInstructorAccount != null)
    {
      instructorAccount = aNewInstructorAccount;
      wasSet = true;
    }
    return wasSet;
  }
}