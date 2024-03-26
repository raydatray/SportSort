package ca.mcgill.ecse321.sportsregistrationw24.dto.courseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;


import java.sql.Date;
import java.time.DayOfWeek;
import java.util.List;

public class CourseOfferingDto {
  private Integer id;
  private Date startDate;
  private Date endDate;
  private List<DayOfWeek> daysOffered;
  private InstructorAccount instructor;
  private Room room;


  public CourseOfferingDto() {
  }

  public CourseOfferingDto(Integer anId, Date aStartDate, Date aEndDate, List<DayOfWeek> aDatesOffered, InstructorAccount instructor, Room aRoom) {
    this.id = anId;
    this.startDate = aStartDate;
    this.endDate = aEndDate;
    this.daysOffered = aDatesOffered;
    this.instructor = instructor;
    this.room = aRoom;
  }

  public CourseOfferingDto(CourseOffering aCourseOffering){
    this(aCourseOffering.getId(), aCourseOffering.getStartDate(), aCourseOffering.getEndDate(), aCourseOffering.getDaysOffered(), aCourseOffering.getInstructorAccount(), aCourseOffering.getRoom());
  }
  public Integer getId() { return this.id; }
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

