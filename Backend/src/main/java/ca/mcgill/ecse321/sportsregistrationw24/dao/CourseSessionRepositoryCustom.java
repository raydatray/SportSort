package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.*;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface CourseSessionRepositoryCustom {
  Optional<List<CourseSession>> findCourseSessionsByFilters(Date startDate, Date endDate,
                                                            Time startTime, Time endTime,
                                                            CourseType courseType,
                                                            List<DayOfWeek> daysOffered,
                                                            CourseOffering courseOffering,
                                                            Room room,
                                                            InstructorAccount instructor);
}
