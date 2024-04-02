package ca.mcgill.ecse321.sportsregistrationw24.dto;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;


public class CourseTypeDTO {
  private Integer id;
  private String courseName;
  private boolean approval;

  public CourseTypeDTO(){}
  public CourseTypeDTO(Integer aId, String aCourseName, boolean approval){
    this.id = aId;
    this.courseName = aCourseName;
    this.approval = approval;
  }
  public CourseTypeDTO(CourseType aCourseType){
    this(aCourseType.getId(), aCourseType.getCourseName(), aCourseType.getApproved());
  }

  public Integer getId() { return this.id; }
  public String getCourseName() {return this.courseName;}
  public boolean getApprovalStatus() {return this.approval;}
}
