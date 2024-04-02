package ca.mcgill.ecse321.sportsregistrationw24.dto.CourseOffering;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.List;

public class CourseOfferingDTO {
  private Integer id;
  private Date startDate;
  private Date endDate;
  private List<DayOfWeek> daysOffered;
  private Integer instructorId;
  private Integer roomId;
  private Integer courseTypeId;

  public CourseOfferingDTO() {}

  public CourseOfferingDTO(Integer aId, Date aStartDate, Date aEndDate, List<DayOfWeek> aDaysOffered, Integer aInstructorId, Integer aRoomId, Integer aCourseTypeId) {
    this.id = aId;
    this.startDate = aStartDate;
    this.endDate = aEndDate;
    this.daysOffered = aDaysOffered;
    this.instructorId = aInstructorId;
    this.roomId = aRoomId;
    this.courseTypeId = aCourseTypeId;
  }

  public CourseOfferingDTO(CourseOffering aCourseOffering) {
    this (aCourseOffering.getId(), aCourseOffering.getStartDate(), aCourseOffering.getEndDate(), aCourseOffering.getDaysOffered(), aCourseOffering.getInstructorAccount().getId(), aCourseOffering.getRoom().getId(), aCourseOffering.getCourseType().getId());
  }
}
