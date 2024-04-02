package ca.mcgill.ecse321.sportsregistrationw24.dto.CourseSession;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseSession;

import java.sql.Time;
import java.sql.Date;

public class CourseSessionDto {
  private Integer id;
  private Date date;
  private Time startTime;
  private Time endTime;
  private Integer courseOfferingId;

  public CourseSessionDto() {}

  public CourseSessionDto(Integer id, Date date, Time startTime, Time endTime, Integer courseOfferingId) {
    this.id = id;
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public CourseSessionDto(CourseSession aCourseSession) {
    this(aCourseSession.getId(), aCourseSession.getDate(), aCourseSession.getStartTime(), aCourseSession.getEndTime(), aCourseSession.getCourseOffering().getId());
  }

  public Integer getId() { return this.id; }
  public Date getDate() { return this.date; }
  public Time getStartTime() { return this.startTime; }
  public Time getEndTime() { return this.endTime; }
}
