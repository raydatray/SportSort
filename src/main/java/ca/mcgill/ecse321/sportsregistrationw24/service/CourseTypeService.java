package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseTypeService {
  @Autowired
  private CourseTypeRepository courseTypeRepository;

  @Autowired
  private UserAccountRepository userAccountRepository;

  @Transactional
  public CourseType createCourseType (String aCourseName, String userType){
    if (!userType.equals("INSTRUCTOR")){
      throw new IllegalArgumentException("Only instructors can create course types!");
    }
    CourseType courseType = new CourseType();
    courseType.setCourseName(aCourseName);
    courseTypeRepository.save(courseType);
    return courseType;
  }

  @Transactional
  public CourseType getCourseType(Integer aId, String userToken) {
    UserAccount user = getUser(userToken);

    // Customers cannot view course types
    if (user.getUserType().equals("CUSTOMER")){
      throw new IllegalArgumentException("Customers cannot view course types!");
    }
    return courseTypeRepository.findById(aId).orElse(null);
  }

  @Transactional
  public List<CourseType> getAllCourseTypes(String userToken) {
    UserAccount user = getUser(userToken);

    // Customers cannot view course types
    if (user.getUserType().equals("CUSTOMER")){
      throw new IllegalArgumentException("Customers cannot view course types!");
    }
    return toList(courseTypeRepository.findAll());
  }

  @Transactional
  public void updateCourseTypeApproval(Integer aId, boolean approval, String userToken) {
    UserAccount user = getUser(userToken);

    // only the owner can approve
    if (!user.getUserType().equals("OWNER")){
      throw new IllegalArgumentException("Only the owner can approve course types!");
    }

    CourseType courseType = getCourseType(aId, userToken);

    if (courseType == null) {
      throw new IllegalArgumentException("Course Type does not exist!");
    }

    courseType.setApproved(approval);
    courseTypeRepository.save(courseType);
  }
  private <T> List<T> toList(Iterable<T> iterable){
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }

  @Transactional
  public void deleteCourseType(Integer aId, String userToken) {
    UserAccount user = getUser(userToken);
    if (!user.getUserType().equals("OWNER")){
      throw new IllegalArgumentException("Only the owner can delete course types!");
    }
    CourseType courseType = getCourseType(aId, userToken);
    if (courseType == null) {
      throw new IllegalArgumentException("Course Type does not exist!");
    }
    courseTypeRepository.delete(courseType);
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
