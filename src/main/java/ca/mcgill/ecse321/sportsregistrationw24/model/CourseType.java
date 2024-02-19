package ca.mcgill.ecse321.sportsregistrationw24.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coursetype")
// line 32 "SportsCenter.ump"
public class CourseType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  @Id
  //CourseType Attributes
  private Integer id;
  private String courseName;
  private boolean approved;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CourseType(Integer aId, String aCourseName, boolean aApproved)
  {
    id = aId;
    courseName = aCourseName;
    approved = aApproved;
  }

  public CourseType() {

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

  public boolean setCourseName(String aCourseName)
  {
    boolean wasSet = false;
    courseName = aCourseName;
    wasSet = true;
    return wasSet;
  }

  public boolean setApproved(boolean aApproved)
  {
    boolean wasSet = false;
    approved = aApproved;
    wasSet = true;
    return wasSet;
  }

  public Integer getId()
  {
    return id;
  }

  public String getCourseName()
  {
    return courseName;
  }

  public boolean getApproved()
  {
    return approved;
  }

}