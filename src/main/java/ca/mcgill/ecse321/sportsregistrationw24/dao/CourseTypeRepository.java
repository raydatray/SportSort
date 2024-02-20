package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourseTypeRepository extends CrudRepository<CourseType, Integer>{
    Optional<CourseType> findByCourseName(String courseName);
    Optional<CourseType> findByApproved(boolean approval);

    void deleteByCourseName(String courseName);

}
