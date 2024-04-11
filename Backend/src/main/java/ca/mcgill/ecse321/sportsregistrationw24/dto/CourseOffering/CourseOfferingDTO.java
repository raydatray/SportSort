package ca.mcgill.ecse321.sportsregistrationw24.dto.CourseOffering;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.List;

public class CourseOfferingDTO {
  private Integer id;
  private Date startDate;
  private Date endDate;
  private Integer price;
  private List<DayOfWeek> daysOffered;
  private Integer instructorId;
  private Integer roomId;
  private Integer courseTypeId;

  public CourseOfferingDTO() {}

  public CourseOfferingDTO(Integer aId, Date aStartDate, Date aEndDate, Integer aPrice, List<DayOfWeek> aDaysOffered, Integer aInstructorId, Integer aRoomId, Integer aCourseTypeId) {
    this.id = aId;
    this.startDate = aStartDate;
    this.endDate = aEndDate;
    this.price = aPrice;
    this.daysOffered = aDaysOffered;
    this.instructorId = aInstructorId;
    this.roomId = aRoomId;
    this.courseTypeId = aCourseTypeId;
  }

  public CourseOfferingDTO(CourseOffering aCourseOffering) {
    this (aCourseOffering.getId(), aCourseOffering.getStartDate(), aCourseOffering.getEndDate(), aCourseOffering.getPrice(), aCourseOffering.getDaysOffered(), aCourseOffering.getInstructorAccount().getId(), aCourseOffering.getRoom().getId(), aCourseOffering.getCourseType().getId());
  }

  public Integer getId() { return this.id; }
  public Date getStartDate() { return this.startDate; }
  public Date getEndDate() { return this.endDate; }
  public Integer getPrice() { return this.price; }
  public List<DayOfWeek> getDaysOffered() { return this.daysOffered; }
  public Integer getInstructorId() { return this.instructorId; }
  public Integer getRoomId() { return this.roomId; }
  public Integer getCourseTypeId() { return this.courseTypeId; }
}
