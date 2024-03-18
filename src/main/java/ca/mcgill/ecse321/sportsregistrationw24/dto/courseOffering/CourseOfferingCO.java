package ca.mcgill.ecse321.sportsregistrationw24.dto.courseOffering;


import java.sql.Date;
import java.time.DayOfWeek;
import java.util.List;

public class CourseOfferingCO {
  private Date startDate;
  private Date endDate;
  private List<DayOfWeek> daysOffered;
  private String instructorToken;
  private Integer roomId;

  public CourseOfferingCO() {
  }

  public CourseOfferingCO(Date aStartDate, Date aEndDate, List<DayOfWeek> aDaysOffered, String aInstructorToken, Integer aRoomId) {
    this.startDate = aStartDate;
    this.endDate = aEndDate;
    this.daysOffered = aDaysOffered;
    this.instructorToken = aInstructorToken;
    this.roomId = aRoomId;
  }
  public Date getStartDate() {
    return this.startDate;
  }
  public Date getEndDate() {
    return this.endDate;
  }
  public List<DayOfWeek> getDaysOffered() { return this.daysOffered; }
  public String getInstructorToken() { return this.instructorToken; }
  public Integer getRoomId() {return this.roomId; }
}

