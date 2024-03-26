package ca.mcgill.ecse321.sportsregistrationw24.dto.courseType;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;

public class CourseTypeDto {
  private String courseName;
  private boolean approval;

  public CourseTypeDto(){}

  public CourseTypeDto(String aCourseName, boolean approval){
    this.courseName = aCourseName;
    this.approval = approval;
  }

  public CourseTypeDto(CourseType aCourseType){
    this(aCourseType.getCourseName(), aCourseType.getApproved());
  }
  public String getCourseName() {return this.courseName;}
  public boolean getApprovalStatus() {return this.approval;}
}
