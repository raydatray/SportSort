package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import org.springframework.data.repository.CrudRepository;

public interface CourseTypeRepository extends CrudRepository<CourseType, Integer>{
    CourseType findByCourseName(String courseName);
    void deleteByCourseName(String courseName);
    boolean existsByCourseName(String courseName);

}
