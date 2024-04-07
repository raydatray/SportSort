package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.StaffAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.*;

@Service
public class CourseTypeService {
  @Autowired
  private CourseTypeRepository courseTypeRepository;
  @Autowired
  private UserAccountRepository userAccountRepository;

  @Transactional
  public void createCourseType (String userToken, String aCourseName){
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("INSTRUCTOR")){
      throw new IllegalArgumentException("Only instructors can create course types!");
    }

    if (aCourseName.trim().isEmpty()){
      throw new IllegalArgumentException("Course name cannot be empty!");
    }

    for (CourseType courseType : courseTypeRepository.findAll()) {
      if (courseType.getCourseName().equals(aCourseName)) {
        throw new IllegalArgumentException("A course type with this name already exists!");
      }
    }

    CourseType courseType = new CourseType();
    courseType.setCourseName(aCourseName);
    courseType.setStaffAccount((StaffAccount) user);
    courseTypeRepository.save(courseType);
  }

  //Do we even need this?
  @Transactional
  public CourseType getCourseType(String userToken, Integer aId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    // Customers cannot view course types
    if (user.getUserType().equals("CUSTOMER")){
      throw new IllegalArgumentException("Customers cannot view course types!");
    }

    return courseTypeRepository.findById(aId).orElse(null);
  }

  @Transactional
  public List<CourseType> getAllCourseTypes(String userToken, Boolean approved, Boolean rejected, Integer staffId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    // Customers cannot view course types
    if (user.getUserType().equals("CUSTOMER")){
      throw new IllegalArgumentException("Customers cannot view course types!");
    }

    UserAccount staff = null;

    if (staffId != null) {
      staff = userAccountRepository.findById(staffId).orElse(null);

      if (staff == null) {
        throw new IllegalArgumentException("Staff does not exist!");
      }
    }

    List<CourseType> foundCourseTypes = courseTypeRepository.findByFilters(approved, rejected, (StaffAccount) staff).orElse(null);

    if (foundCourseTypes == null) {
      throw new IllegalArgumentException("No course types found!");
    }
    return foundCourseTypes;
  }

  @Transactional
  public List<CourseType> getAllApprovedCourseTypes() {
    List<CourseType> foundCourseTypes = courseTypeRepository.findByApproved(true).orElse(null);

    if (foundCourseTypes == null) {
      throw new IllegalArgumentException("No approved course types found!");
    }

    return foundCourseTypes;
  }

  @Transactional
  public List<CourseType> getProposedCourseTypes(String userToken) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    // Customers cannot view course types
    if (!user.getUserType().equals("INSTRUCTOR")){
      throw new IllegalArgumentException("Customers cannot view course types!");
    }

    List<CourseType> foundCourseTypes = courseTypeRepository.findByStaffAccount((StaffAccount) user).orElse(null);

    if (foundCourseTypes == null) {
      throw new IllegalArgumentException("No proposed course types found!");
    }

    return foundCourseTypes;
  }

  @Transactional
  public void updateCourseTypeApproval(String userToken, Integer aId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    // only the owner can approve
    if (!user.getUserType().equals("OWNER")){
      throw new IllegalArgumentException("Only the owner can approve course types!");
    }

    CourseType courseType = courseTypeRepository.findById(aId).orElse(null);

    if (courseType == null) {
      throw new IllegalArgumentException("Course Type does not exist!");
    }

    if (courseType.getRejected()){
      throw new IllegalArgumentException("Course Type has been rejected!");
    }

    courseType.setApproved(!courseType.getApproved());
    courseTypeRepository.save(courseType);
  }

  @Transactional
  public void updateCourseTypeRejection(String userToken, Integer aId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    // only the owner can reject
    if (!user.getUserType().equals("OWNER")){
      throw new IllegalArgumentException("Only the owner can reject course types!");
    }

    CourseType courseType = courseTypeRepository.findById(aId).orElse(null);

    if (courseType == null) {
      throw new IllegalArgumentException("Course Type does not exist!");
    }

    courseType.setRejected(!courseType.getRejected());
    courseTypeRepository.save(courseType);
  }

  @Transactional
  public void deleteCourseType(String userToken, Integer aId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("OWNER")){
      throw new IllegalArgumentException("Only the owner can delete course types!");
    }

    CourseType courseType = courseTypeRepository.findById(aId).orElse(null);

    if (courseType == null) {
      throw new IllegalArgumentException("Course Type does not exist!");
    }

    courseTypeRepository.delete(courseType);
  }
}
