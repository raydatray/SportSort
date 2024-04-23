package ca.mcgill.ecse321.sportsregistrationw24.dto;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;


public class CourseTypeDTO {
  private Integer id;
  private String courseName;
  private Boolean approval;
  private Boolean rejected;
  private Integer staffAccountId;

  public CourseTypeDTO() {
  }

  public CourseTypeDTO(Integer aId, String aCourseName, Boolean approval, Boolean rejected, Integer staffAccountId) {
    this.id = aId;
    this.courseName = aCourseName;
    this.approval = approval;
    this.rejected = rejected;
    this.staffAccountId = staffAccountId;
  }

  public CourseTypeDTO(CourseType aCourseType) {
    this(aCourseType.getId(), aCourseType.getCourseName(), aCourseType.getApproved(), aCourseType.getRejected(), aCourseType.getStaffAccount().getId());
  }

  public Integer getId() {
    return this.id;
  }

  public String getCourseName() {
    return this.courseName;
  }

  public Boolean getApprovalStatus() {
    return this.approval;
  }

  public Boolean getRejectedStatus() {
    return this.rejected;
  }

  public Integer getStaffAccountId() {
    return this.staffAccountId;
  }
}
