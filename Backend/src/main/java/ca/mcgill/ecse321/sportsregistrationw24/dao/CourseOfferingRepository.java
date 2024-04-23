package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface CourseOfferingRepository extends CrudRepository<CourseOffering, Integer>, CourseOfferingRepositoryCustom {
  Optional<List<CourseOffering>> findByInstructorAccount(InstructorAccount instructor);

  Optional<List<CourseOffering>> findByCourseType(CourseType courseType);

  Optional<List<CourseOffering>> findByRoom(Room room);

  Optional<List<CourseOffering>> findByStartDate(Date date);

  Optional<List<CourseOffering>> findByEndDate(Date date);

  //No delete by courseType as it will cascade down to courseOffering
  //No delete by instructorAccount as it will cascade down to courseOffering
}

