package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.Instructor;
import ca.mcgill.ecse321.sportsregistrationw24.model.SportClass;
import org.springframework.data.repository.CrudRepository;

public interface SportClassRepository extends CrudRepository<SportClass, Integer> {
    Integer getFloorNumber(Integer classId);
    Integer getRoomNumber(Integer classId);
    SportClass findClassById(Integer classId);
    Instructor findClassInstructor(Integer classId);
}

