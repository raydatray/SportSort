package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.InstructorAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseOfferingService {

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private InstructorAccountRepository instructorAccountRepository;

    @Transactional
    public CourseOffering createCourseOffering(Date aStartDate, Date aEndDate, Room aRoom, Integer aId){
        CourseOffering courseOffering = new CourseOffering();
        courseOffering.setStartDate(aStartDate);
        courseOffering.setEndDate(aEndDate);
        courseOffering.setRoom(aRoom);
        courseOffering.setId(aId);
        courseOfferingRepository.save(courseOffering);
        return courseOffering;
    }

    @Transactional
    public CourseOffering getCourseOfferingById(Integer aId, UserAccount user) {
        if (user.getUserType().equals("INSTRUCTOR")) {
            Optional<InstructorAccount> optional = instructorAccountRepository.findByEmail(user.getEmail());
            InstructorAccount instructor = optional.orElse(null);

            if (instructor == null) {
                throw new IllegalArgumentException("Instructor does not exist!");
            }

            Optional<CourseOffering> optional_1 = courseOfferingRepository.findById(aId);
            if (getCourseOfferingByInstructor(instructor).contains(optional_1.orElse(null))) {
                return courseOfferingRepository.findById(aId).orElse(null);
            } else {
                throw new IllegalArgumentException("Instructor does not teach this course!");
            }
        }
        return courseOfferingRepository.findById(aId).orElse(null);
    }
    @Transactional
    public List<CourseOffering> getCourseOfferingByInstructor(InstructorAccount instructor) {
        return courseOfferingRepository.findByInstructorAccount(instructor).orElse(null);
    }

    /*@Transactional
    public void updateCourseOffering(Date aStartDate, Date aEndDate, Room aRoom, Integer aId) {
        CourseOffering courseOffering = courseOfferingRepository.findById(aId).orElse(null);

        if (courseOffering== null) {
            throw new IllegalArgumentException("Course Offering does not exist!");
        }

        courseOffering.setStartDate(aStartDate);
        courseOffering.setEndDate(aEndDate);
        courseOffering.setRoom(aRoom);
        courseOffering.setId(aId);
        courseOfferingRepository.save(courseOffering);
    } */

    @Transactional
    public void deleteCourseOffering(Integer aId) {
        CourseOffering courseOffering = courseOfferingRepository.findById(aId).orElse(null);

        if (courseOffering == null) {
            throw new IllegalArgumentException("Course Offering does not exist!");
        }

        courseOfferingRepository.delete(courseOffering);
    }
    @Transactional
    public List<CourseOffering> getAllCourseOfferings() {
        return toList(courseOfferingRepository.findAll());
    }

    private <T> List<T> toList(Iterable<T> iterable){
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
