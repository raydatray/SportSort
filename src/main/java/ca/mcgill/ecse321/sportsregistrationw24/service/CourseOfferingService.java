package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseOfferingService {

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

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
    public CourseOffering getCourseOffering(Integer aId) {
        return courseOfferingRepository.findById(aId).orElse(null);
    }

    @Transactional
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
    }

    @Transactional
    public void deleteCourseOffering(Integer aId) {
        CourseOffering courseOffering = courseOfferingRepository.findById(aId).orElse(null);

        if (courseOffering== null) {
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
