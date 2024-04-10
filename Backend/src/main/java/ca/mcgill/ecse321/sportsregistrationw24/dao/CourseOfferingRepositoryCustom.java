package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface CourseOfferingRepositoryCustom {
  Optional<List<CourseOffering>> findCourseOfferingsByFilters(Date startDate, Date endDate,
                                                              Time startTime, Time endTime,
                                                              Integer highPrice,
                                                              List<CourseType> courseTypes,
                                                              List<DayOfWeek> daysOffered,
                                                              List<Room> rooms,
                                                              List<InstructorAccount> instructors);
}
