package ca.mcgill.ecse321.sportsregistrationw24.dto.courseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.List;

public class CourseOfferingCO {
  private Date startDate;
  private Date endDate;
  private List<DayOfWeek> daysOffered;
  private InstructorAccount instructor;
  private Room room;
  private String userType;


  public CourseOfferingCO() {
  }

  public CourseOfferingCO(Date aStartDate, Date aEndDate, List<DayOfWeek> aDaysOffered, InstructorAccount instructor, Room aRoom, String aUserType) {
    this.startDate = aStartDate;
    this.endDate = aEndDate;
    this.daysOffered = aDaysOffered;
    this.instructor = instructor;
    this.room = aRoom;
    this.userType = aUserType;
  }
  public Date getStartDate() {
    return this.startDate;
  }
  public Date getEndDate() {
    return this.endDate;
  }
  public List<DayOfWeek> getDaysOffered() { return this.daysOffered; }
  public InstructorAccount getInstructor() { return this.instructor; }
  public Room getRoom() {return this.room; }
  public String getUserType() {return this.userType;}
}

