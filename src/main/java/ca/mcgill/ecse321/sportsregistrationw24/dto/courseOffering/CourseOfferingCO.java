package ca.mcgill.ecse321.sportsregistrationw24.dto.courseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;

import java.sql.Date;

public class CourseOfferingCO {
  private Date startDate;
  private Date endDate;
  private Room room;
  private String userType;


  public CourseOfferingCO() {
  }

  public CourseOfferingCO(Date aStartDate, Date aEndDate, Room aRoom, String aUserType) {
    this.startDate = aStartDate;
    this.endDate = aEndDate;
    this.room = aRoom;
    this.userType = aUserType;
  }
  public Date getStartDate() {
    return this.startDate;
  }
  public Date getEndDate() {
    return this.endDate;
  }
  public Room getRoom() {return this.room; }
  public String getUserType() {return this.userType;}
}

