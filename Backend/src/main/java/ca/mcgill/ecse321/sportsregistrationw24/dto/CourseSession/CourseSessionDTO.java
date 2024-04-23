package ca.mcgill.ecse321.sportsregistrationw24.dto.CourseSession;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseSession;

import java.sql.Date;
import java.sql.Time;

public class CourseSessionDTO {
  private Integer id;
  private Date date;
  private Time startTime;
  private Time endTime;
  private Integer courseOfferingId;

  public CourseSessionDTO() {
  }

  public CourseSessionDTO(Integer id, Date date, Time startTime, Time endTime, Integer courseOfferingId) {
    this.id = id;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public CourseSessionDTO(CourseSession aCourseSession) {
    this(aCourseSession.getId(), aCourseSession.getDate(), aCourseSession.getStartTime(), aCourseSession.getEndTime(), aCourseSession.getCourseOffering().getId());
  }

  public Integer getId() {
    return this.id;
  }

  public Date getDate() {
    return this.date;
  }

  public Time getStartTime() {
    return this.startTime;
  }

  public Time getEndTime() {
    return this.endTime;
  }
}
