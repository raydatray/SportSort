package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courseoffering")
// line 38 "SportsCenter.ump"
public class CourseOffering
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  @Id
  //CourseOffering Attributes
  private Integer id;
  private Date startDate;
  private Date endDate;

  @OneToOne
  //CourseOffering Associations
  private Room room;
  @OneToMany
  private List<CourseType> courseType;
  @OneToMany
  private List<InstructorAccount> instructorAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CourseOffering(Integer aId, Date aStartDate, Date aEndDate, Room aRoom, List<CourseType> aCourseType, List<InstructorAccount> aInstructorAccount)
  {
    id = aId;
    startDate = aStartDate;
    endDate = aEndDate;
    if (!setRoom(aRoom))
    {
      throw new RuntimeException("Unable to create CourseOffering due to aRoom. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    courseType = aCourseType;
    instructorAccount = aInstructorAccount;
  }

  public CourseOffering() {

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
  public List<CourseType> getCourseType()
  {
    return courseType;
  }
  /* Code from template association_GetOne */
  public List<InstructorAccount> getInstructorAccount()
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

  public boolean addCourseType(CourseType aCourseType) {
    if (courseType == null) {
      courseType = new ArrayList<>();
    }
    return courseType.add(aCourseType);
  }

  public boolean removeCourseType(CourseType aCourseType) {
    if (courseType != null && courseType.contains(aCourseType)) {
      return courseType.remove(aCourseType);
    }
    return false;
  }
}