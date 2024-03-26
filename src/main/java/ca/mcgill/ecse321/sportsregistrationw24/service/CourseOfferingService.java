package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseOfferingService {

  @Autowired
  private CourseOfferingRepository courseOfferingRepository;

  @Autowired
  private CourseTypeRepository courseTypeRepository;

  @Autowired
  private UserAccountRepository userAccountRepository;

  @Autowired
  private RoomRepository roomRepository;

  @Transactional
  public CourseOffering createCourseOffering(Date aStartDate, Date aEndDate, List<DayOfWeek> aDaysOffered, String aInstructorToken, Integer aRoomId, Integer aCourseTypeId){
    UserAccount user = getUser(aInstructorToken);

    if (!user.getUserType().equals("INSTRUCTOR")){
      throw new IllegalArgumentException("Only instructors can create course offerings!");
    }

    InstructorAccount foundInstructor = (InstructorAccount) user;

    Room foundRoom = roomRepository.findById(aRoomId).orElseThrow(() -> new IllegalArgumentException("Room does not exist!"));
    CourseType foundCourseType = courseTypeRepository.findById(aCourseTypeId).orElseThrow(() -> new IllegalArgumentException("Course Type does not exist!"));

    CourseOffering courseOffering = new CourseOffering();

    courseOffering.setStartDate(aStartDate);
    courseOffering.setEndDate(aEndDate);
    courseOffering.setDaysOffered(aDaysOffered);
    courseOffering.setInstructorAccount(foundInstructor);
    courseOffering.setRoom(foundRoom);
    courseOffering.setCourseType(foundCourseType);

    courseOfferingRepository.save(courseOffering);

    return courseOffering;
  }

  @Transactional
  public CourseOffering getCourseOfferingById(Integer aId, String userToken) {
    UserAccount user = getUser(userToken);

    /**
    // different results depending on user type
    if (user.getUserType().equals("INSTRUCTOR")) {
      InstructorAccount instructor = (InstructorAccount) user;
      Optional<CourseOffering> optional_1 = courseOfferingRepository.findById(aId);
      if (getCourseOfferingByInstructor(instructor).contains(optional_1.orElse(null))) {
        return courseOfferingRepository.findById(aId).orElse(null);
      } else {
        throw new IllegalArgumentException("Instructor does not teach this course!");
      }
    }
    **/
    return courseOfferingRepository.findById(aId).orElse(null);
  }

  @Transactional
  public List<CourseOffering> getCourseOfferingByInstructor(InstructorAccount instructor) {
    return courseOfferingRepository.findByInstructorAccount(instructor).orElse(null);
  }

  @Transactional
  public void deleteCourseOffering(Integer aId, String userToken) {
    UserAccount user = getUser(userToken);

    if (user.getUserType().equals("CUSTOMER")){
      throw new IllegalArgumentException("Customers cannot delete course offerings!");
    }
    CourseOffering courseOffering = courseOfferingRepository.findById(aId).orElse(null);

    if (courseOffering == null) {
      throw new IllegalArgumentException("Course Offering does not exist!");
    }

    courseOfferingRepository.delete(courseOffering);
  }
  @Transactional
  public List<CourseOffering> getAllCourseOfferings(String userToken) {
    UserAccount user = getUser(userToken);

    if (user.getUserType().equals("INSTRUCTOR")) {
      InstructorAccount instructor = (InstructorAccount) user;
      return getCourseOfferingByInstructor(instructor);
    }
    return toList(courseOfferingRepository.findAll());
  }

  private <T> List<T> toList(Iterable<T> iterable){
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }

  private UserAccount getUser(String userToken) {
    Optional<UserAccount> optional = userAccountRepository.findUserByToken(userToken);
    UserAccount user = optional.orElse(null);

    if (user == null) {
      throw new IllegalArgumentException("User does not exist!");
    }
    return user;
  }
}
