package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "coursesession")
public class CourseSession {
  @Id
  @GeneratedValue
  private Integer id;
  private Date date;
  private Time startTime;
  private Time endTime;

  @ManyToOne
  @JoinColumn(name = "course_offering_id")
  private CourseOffering courseOffering;

  public CourseSession(Date aDate, Time aStartTime, Time aEndTime, CourseOffering aCourseOffering) {
    this.date = aDate;
    this.startTime = aStartTime;
    this.endTime = aEndTime;
    if (!setCourseOffering(aCourseOffering)) {
      throw new RuntimeException("Unable to create CourseSession due to aCourseOffering. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  public CourseSession() {}

  public void setDate(Date aDate) { this.date = aDate; };

  public void setStartTime(Time aStartTime) { this.startTime = aStartTime; }

  public void setEndTime(Time aEndTime) { this.endTime = aEndTime; }

  public boolean setCourseOffering(CourseOffering aNewCourseOffering) {
    boolean wasSet = false;
    if (aNewCourseOffering != null) {
      courseOffering = aNewCourseOffering;
      wasSet = true;
    }
    return wasSet;
  }

  public Integer getId()
  {
    return this.id;
  }

  public Date getDate()
  {
    return this.date;
  }

  public Time getStartTime()
  {
    return this.startTime;
  }

  public Time getEndTime()
  {
    return this.endTime;
  }

  public CourseOffering getCourseOffering()
  {
    return this.courseOffering;
  }

}