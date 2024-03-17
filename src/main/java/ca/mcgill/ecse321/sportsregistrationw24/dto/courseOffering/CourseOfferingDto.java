package ca.mcgill.ecse321.sportsregistrationw24.dto.courseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import org.springframework.jndi.support.SimpleJndiBeanFactory;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.List;

public class CourseOfferingDto {
  private Date startDate;
  private Date endDate;
  private List<DayOfWeek> daysOffered;
  private InstructorAccount instructor;
  private Room room;


  public CourseOfferingDto() {
  }

  public CourseOfferingDto(Date aStartDate, Date aEndDate, List<DayOfWeek> aDatesOffered, InstructorAccount instructor, Room aRoom) {
    this.startDate = aStartDate;
    this.endDate = aEndDate;
    this.daysOffered = aDatesOffered;
    this.instructor = instructor;
    this.room = aRoom;
  }

  public CourseOfferingDto(CourseOffering aCourseOffering){
    this(aCourseOffering.getStartDate(), aCourseOffering.getEndDate(), aCourseOffering.getDaysOffered(), aCourseOffering.getInstructorAccount(), aCourseOffering.getRoom());
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
}

