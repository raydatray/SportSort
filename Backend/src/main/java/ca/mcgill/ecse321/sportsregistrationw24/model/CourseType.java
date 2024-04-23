package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.*;

@Entity
@Table(name = "coursetype")
public class CourseType {
  @Id
  @GeneratedValue
  private Integer id;
  private String courseName;
  private boolean approved;
  private boolean rejected;
  @ManyToOne
  private StaffAccount staffAccount;

  public CourseType() {}
  public CourseType(String aCourseName, StaffAccount aStaffAccount) {
    this.courseName = aCourseName;
    this.approved = false;
    this.rejected = false;
    this.staffAccount = aStaffAccount;
  }

  public void setCourseName(String aCourseName) {
    this.courseName = aCourseName;
  }
  public void setApproved(boolean aApproved) {
    this.approved = aApproved;
  }
  public void setRejected(boolean aRejected) {
    this.rejected = aRejected;
  }
  public void setStaffAccount(StaffAccount aStaffAccount) {
    this.staffAccount = aStaffAccount;
  }

  public Integer getId() {
    return this.id;
  }
  public String getCourseName() {
    return this.courseName;
  }
  public boolean getApproved() {
    return this.approved;
  }
  public boolean getRejected() {
    return this.rejected;
  }
  public StaffAccount getStaffAccount() {
    return this.staffAccount;
  }
}