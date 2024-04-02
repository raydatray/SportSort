package ca.mcgill.ecse321.sportsregistrationw24.dao;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.StaffAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseTypeRepository extends CrudRepository<CourseType, Integer>{
    Optional<CourseType> findByCourseName(String courseName);
    Optional<List<CourseType>> findByApproved(boolean approval);
    Optional<List<CourseType>> findByRejected(boolean rejected);
    Optional<List<CourseType>> findByInstructorAccount(InstructorAccount instructorAccount);
    @Query("SELECT c FROM CourseType c WHERE (:approval is null or c.approved = :approval) and (:rejected is null or c.rejected = :rejected) and (:staff is null or c.staffAccount = :staff)")
    Optional<List<CourseType>> findByFilters(@Param("approval") Boolean approval, @Param("rejected") Boolean rejected, @Param("staff") StaffAccount staff);
    void deleteByCourseName(String courseName);
}
