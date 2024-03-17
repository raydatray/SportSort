package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.InstructorAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
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

  @Autowired
  private UserAccountRepository userAccountRepository;

  @Transactional
  public CourseOffering createCourseOffering(Date aStartDate, Date aEndDate, Room aRoom, String userType){
    if (!userType.equals("INSTRUCTOR")){
      throw new IllegalArgumentException("Only instructors can create course offerings!");
    }
    CourseOffering courseOffering = new CourseOffering();
    courseOffering.setStartDate(aStartDate);
    courseOffering.setEndDate(aEndDate);
    courseOffering.setRoom(aRoom);
    courseOfferingRepository.save(courseOffering);
    return courseOffering;
  }

  @Transactional
  public CourseOffering getCourseOfferingById(Integer aId, String userEmail) {
    UserAccount user = getUser(userEmail);

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
    return courseOfferingRepository.findById(aId).orElse(null);
  }
  @Transactional
  public List<CourseOffering> getCourseOfferingByInstructor(InstructorAccount instructor) {
    return courseOfferingRepository.findByInstructorAccount(instructor).orElse(null);
  }

  @Transactional
  public void deleteCourseOffering(Integer aId, String userEmail) {
    UserAccount user = getUser(userEmail);

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
  public List<CourseOffering> getAllCourseOfferings(String userEmail) {
    UserAccount user = getUser(userEmail);

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

  private UserAccount getUser(String userEmail) {
    Optional<UserAccount> optional = userAccountRepository.findUserByEmail(userEmail);
    UserAccount user = optional.orElse(null);

    if (user == null) {
      throw new IllegalArgumentException("User does not exist!");
    }
    return user;
  }
}
