package ca.mcgill.ecse321.sportsregistrationw24.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
  private Integer price;
  @ElementCollection
  @Enumerated(EnumType.STRING)
  @Fetch(FetchMode.JOIN)
  private List<DayOfWeek> daysOffered;
  @OneToOne
  private Room room;
  @ManyToOne
  private CourseType courseType;
  @ManyToOne
  private InstructorAccount instructorAccount;
  @OneToMany(
    mappedBy = "courseOffering",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY
  )
  private List<CourseSession> courseSessions;

  public CourseOffering() {}
  public CourseOffering(Date aStartDate, Date aEndDate, Integer aPrice, ArrayList<DayOfWeek> someDaysOffered, Room aRoom, CourseType aCourseType, InstructorAccount aInstructorAccount) {
    this.startDate = aStartDate;
    this.endDate = aEndDate;
    this.price = aPrice;
    this.daysOffered = someDaysOffered;
    this.room = aRoom;
    this.courseType = aCourseType;
    this.instructorAccount = aInstructorAccount;
  }

  public void setStartDate(Date aStartDate) { this.startDate = aStartDate; }
  public void setEndDate(Date aEndDate) { this.endDate = aEndDate; }
  public void setPrice(Integer aPrice) { this.price = aPrice; }
  public void setDaysOffered(List<DayOfWeek> aDaysOffered) { this.daysOffered = aDaysOffered; }
  public void setInstructorAccount(InstructorAccount instructor) { this.instructorAccount = instructor; }
  public void setRoom(Room aNewRoom) { this.room = aNewRoom; }
  public void setCourseType(CourseType aCourseType) { this.courseType = aCourseType; }


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
  public Integer getPrice() { return this.price; }
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