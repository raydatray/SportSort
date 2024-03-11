package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coursetype")
// line 32 "SportsCenter.ump"
public class CourseType {
  @Id
  @GeneratedValue
  //CourseType Attributes
  private Integer id;
  private String courseName;
  private boolean approved;

  public CourseType(String aCourseName) {
    this.courseName = aCourseName;
    this.approved = false;
  }
  public CourseType() {}

  public void setCourseName(String aCourseName) { this.courseName = aCourseName; }

  public void setApproved(boolean aApproved) { this.approved = aApproved; }

  public Integer getId() { return this.id; }

  public String getCourseName() { return this.courseName; }

  public boolean getApproved() { return this.approved; }

}