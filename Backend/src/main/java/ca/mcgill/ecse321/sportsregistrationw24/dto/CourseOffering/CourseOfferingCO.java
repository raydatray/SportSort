package ca.mcgill.ecse321.sportsregistrationw24.dto.CourseOffering;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.List;

public class CourseOfferingCO {
  private Date startDate;
  private Date endDate;
  private Integer price;
  private List<DayOfWeek> daysOffered;
  private Integer roomId;
  private Integer courseTypeId;

  public CourseOfferingCO() {}

  public CourseOfferingCO(Date aStartDate, Date aEndDate, Integer aPrice, List<DayOfWeek> aDaysOffered, Integer aRoomId, Integer aCourseTypeId) {
    this.startDate = aStartDate;
    this.endDate = aEndDate;
    this.price = aPrice;
    this.daysOffered = aDaysOffered;
    this.roomId = aRoomId;
    this.courseTypeId = aCourseTypeId;
  }
  public Date getStartDate() {
    return this.startDate;
  }
  public Date getEndDate() {
    return this.endDate;
  }
  public Integer getPrice() { return this.price; }
  public List<DayOfWeek> getDaysOffered() { return this.daysOffered; }
  public Integer getRoomId() {return this.roomId; }
  public Integer getCourseTypeId() { return this.courseTypeId; }
}

