package ca.mcgill.ecse321.sportsregistrationw24.dto.courseType;

public class CourseTypeCO {
  private String courseName;
  private boolean approval;

  public CourseTypeCO(){}

  public CourseTypeCO(String aCourseName, boolean approval){
    this.courseName = aCourseName;
    this.approval = approval;
  }

  public String getCourseName() {return this.courseName;}
  public boolean getApprovalStatus() {return this.approval;}
}
