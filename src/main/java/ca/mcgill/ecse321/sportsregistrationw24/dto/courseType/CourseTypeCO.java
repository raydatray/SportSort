package ca.mcgill.ecse321.sportsregistrationw24.dto.courseType;

public class CourseTypeCO {
  private String courseName;
  private String userType;

  public CourseTypeCO(){}

  public CourseTypeCO(String aCourseName, String aUserType){
    this.courseName = aCourseName;
    this.userType = aUserType;
  }

  public String getCourseName() {return this.courseName;}
  public String getUserType() {return this.userType;}
}
