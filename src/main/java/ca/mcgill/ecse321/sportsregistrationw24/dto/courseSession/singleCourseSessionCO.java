package ca.mcgill.ecse321.sportsregistrationw24.dto.courseSession;

import java.sql.Date;
import java.sql.Time;

public class singleCourseSessionCO {
  private Date date;
  private Time startTime;
  private Time endTime;
  private Integer courseOfferingId;

  public singleCourseSessionCO() {}

  public singleCourseSessionCO(Date date, Time startTime, Time endTime, Integer courseOfferingId) {
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.courseOfferingId = courseOfferingId;
  }

  public Date getDate() { return this.date; }
  public Time getStartTime() { return this.startTime; }
  public Time getEndTime() { return this.endTime; }
  public Integer getCourseOfferingId() { return this.courseOfferingId; }
}