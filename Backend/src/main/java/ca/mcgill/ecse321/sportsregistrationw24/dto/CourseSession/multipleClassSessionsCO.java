package ca.mcgill.ecse321.sportsregistrationw24.dto.CourseSession;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

public class multipleClassSessionsCO {
  HashMap<DayOfWeek, ArrayList<Time>> dayTimeMapping;
  Integer courseOfferingId;

  public multipleClassSessionsCO() {}

  public multipleClassSessionsCO(HashMap<DayOfWeek, ArrayList<Time>> dayTimeMapping, Integer courseOfferingId) {
    this.dayTimeMapping = dayTimeMapping;
    this.courseOfferingId = courseOfferingId;
  }

  public HashMap<DayOfWeek, ArrayList<Time>> getDayTimeMapping() { return this.dayTimeMapping; }
  public Integer getCourseOfferingId() { return this.courseOfferingId; }
}
