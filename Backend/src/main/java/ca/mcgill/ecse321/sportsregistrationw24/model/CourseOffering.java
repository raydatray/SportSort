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
  private String name;
  public enum difficultyLevel {BEGINNER, INTERMEDIATE, ADVANCED}
  private difficultyLevel difficulty;
  private Integer price;
  private Integer capacity;
  private Date startDate;
  private Date endDate;
  @ElementCollection
  @Enumerated(EnumType.STRING)
  @Fetch(FetchMode.JOIN)
  private List<DayOfWeek> daysOffered;
  @ManyToOne
  private CourseType courseType;
  @ManyToOne
  private InstructorAccount instructorAccount;
  @OneToMany(mappedBy = "courseOffering", fetch = FetchType.LAZY)
  private List<CourseSession> courseSessions;

  public CourseOffering() {}
  public CourseOffering(String aName, difficultyLevel aDifficultyLevel, Integer aPrice, Integer aCapacity, Date aStartDate, Date aEndDate, ArrayList<DayOfWeek> someDaysOffered, CourseType aCourseType, InstructorAccount aInstructorAccount) {
    this.name = aName;
    this.difficulty = aDifficultyLevel;
    this.price = aPrice;
    this.capacity = aCapacity;
    this.startDate = aStartDate;
    this.endDate = aEndDate;
    this.daysOffered = someDaysOffered;
    this.courseType = aCourseType;
    this.instructorAccount = aInstructorAccount;
  }

  public void setName (String aName) { this.name = aName; }
  public void setDifficulty(difficultyLevel aDifficulty) { this.difficulty = aDifficulty; }
  public void setPrice(Integer aPrice) {
    this.price = aPrice;
  }
  public void setCapacity(Integer aCapacity) { this.capacity = aCapacity; }
  public void setStartDate(Date aStartDate) { this.startDate = aStartDate; }
  public void setEndDate(Date aEndDate) {
    this.endDate = aEndDate;
  }
  public void setDaysOffered(List<DayOfWeek> aDaysOffered) {
    this.daysOffered = aDaysOffered;
  }
  public void setCourseType(CourseType aCourseType) {this.courseType = aCourseType; }
  public void setInstructorAccount(InstructorAccount instructor) {
    this.instructorAccount = instructor;
  }

  public Integer getId() { return this.id; }
  public String getName() { return this.name; }
  public difficultyLevel getDifficulty() { return this.difficulty; }
  public Integer getPrice() { return this.price; }
  public Integer getCapacity() { return this.capacity; }
  public Date getStartDate() { return this.startDate; }
  public Date getEndDate() { return this.endDate; }
  public ArrayList<DayOfWeek> getDaysOffered() { return new ArrayList<>(this.daysOffered); }
  public CourseType getCourseType() { return this.courseType; }
  public InstructorAccount getInstructorAccount() { return this.instructorAccount; }
}