package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courseoffering")
public class CourseOffering {
  @Id
  @GeneratedValue
  private Integer id;
  private Date startDate;
  private Date endDate;
  @ElementCollection
  @Enumerated(EnumType.STRING)
  private List<DayOfWeek> daysOffered;

  @OneToOne
  private Room room;
  @ManyToOne
  private CourseType courseType;
  @ManyToOne
  private InstructorAccount instructorAccount;

  public CourseOffering(Date aStartDate, Date aEndDate, ArrayList<DayOfWeek> someDaysOffered, Room aRoom, CourseType aCourseType, InstructorAccount aInstructorAccount) {
    this.startDate = aStartDate;
    this.endDate = aEndDate;
    this.daysOffered = someDaysOffered;
    if (!setRoom(aRoom)) {
      throw new RuntimeException("Unable to create CourseOffering due to aRoom. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    this.courseType = aCourseType;
    this.instructorAccount = aInstructorAccount;
  }
  public CourseOffering() {}

  public void setId(Integer aId) { this.id = aId; }

  public void setStartDate(Date aStartDate) { this.startDate = aStartDate; }

  public void setEndDate(Date aEndDate) { this.endDate = aEndDate; }

  public void setDaysOffered(List<DayOfWeek> aDaysOffered) { this.daysOffered = aDaysOffered; }

  public void setInstructorAccount(InstructorAccount instructor) { this.instructorAccount = instructor; }

  public boolean setRoom(Room aNewRoom) {
    boolean wasSet = false;
    if (aNewRoom != null) {
      room = aNewRoom;
      wasSet = true;
    }
    return wasSet;
  }

  public Integer getId()
  {
    return this.id;
  }

  public Date getStartDate()
  {
    return this.startDate;
  }

  public Date getEndDate()
  {
    return this.endDate;
  }

  public Room getRoom()
  {
    return this.room;
  }

  public ArrayList<DayOfWeek> getDaysOffered() { return new ArrayList<>(this.daysOffered); }

  public CourseType getCourseType()
  {
    return this.courseType;
  }

  public InstructorAccount getInstructorAccount()
  {
    return this.instructorAccount;
  }


}