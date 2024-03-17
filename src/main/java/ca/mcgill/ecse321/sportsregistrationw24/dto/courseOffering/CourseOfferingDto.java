package ca.mcgill.ecse321.sportsregistrationw24.dto.courseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import java.sql.Date;

public class CourseOfferingDto {
  private Date startDate;
  private Date endDate;
  private Room room;


  public CourseOfferingDto() {
  }

  public CourseOfferingDto(Date aStartDate, Date aEndDate, Room aRoom, Integer aId) {
    this.startDate = aStartDate;
    this.endDate = aEndDate;
    this.room = aRoom;
  }

  public CourseOfferingDto(CourseOffering aCourseOffering){
    this(aCourseOffering.getStartDate(), aCourseOffering.getEndDate(), aCourseOffering.getRoom(), aCourseOffering.getId());
  }
  public Date getStartDate() {
    return this.startDate;
  }
  public Date getEndDate() {
    return this.endDate;
  }
  public Room getRoom() {return this.room; }
}

