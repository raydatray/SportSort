package ca.mcgill.ecse321.sportsregistrationw24.dto;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import java.sql.Date;

public class CourseOfferingDto {
    private Date startDate;
    private Date endDate;
    private Room room;
    private Integer id;


    public CourseOfferingDto() {
    }

    public CourseOfferingDto(Date aStartDate, Date aEndDate, Room aRoom, Integer aId) {
        this.startDate = aStartDate;
        this.endDate = aEndDate;
        this.room = aRoom;
        this.id = aId;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public Room getRoom() {return this.room; }
    public Integer getiD() {return this.id; }
}

