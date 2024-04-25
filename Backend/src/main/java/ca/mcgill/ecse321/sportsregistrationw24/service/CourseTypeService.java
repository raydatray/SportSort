package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.StaffAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang3.Validate;

import java.util.List;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;

@Service
public class CourseTypeService {
  private final CourseTypeRepository courseTypeRepository;
  private final UserAccountRepository userAccountRepository;

  @Autowired
  public CourseTypeService(CourseTypeRepository courseTypeRepository, UserAccountRepository userAccountRepository) {
    this.courseTypeRepository = courseTypeRepository;
    this.userAccountRepository = userAccountRepository;
  }

  @Transactional
  public void createCourseType(String userToken, String aCourseName) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    Validate.isTrue(user.getUserType().equals("INSTRUCTOR"), "Only instructors can create course types");
    Validate.notBlank(aCourseName, "Course type name cannot be empty");

    for (CourseType courseType : courseTypeRepository.findAll()) {
      Validate.isTrue(!courseType.getCourseName().equals(aCourseName), "Course type already exists");
    }

    CourseType courseType = new CourseType();
    courseType.setCourseName(aCourseName);
    courseType.setStaffAccount((StaffAccount) user);
    courseTypeRepository.save(courseType);
  }

  @Transactional
  public List<CourseType> getAllCourseTypes(String userToken, Boolean approved, Boolean rejected, Integer aStaffId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    Validate.isTrue(!user.getUserType().equals("CUSTOMER"), "Customers cannot view all course types");

    UserAccount staff = null;

    if (aStaffId != null) {
      staff = userAccountRepository.findById(aStaffId).orElse(null);

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
    if (!user.getUserType().equals("INSTRUCTOR")) {
      throw new IllegalArgumentException("Customers cannot view course types!");
    }

    List<CourseType> foundCourseTypes = courseTypeRepository.findByStaffAccount((StaffAccount) user).orElse(null);

    if (foundCourseTypes == null) {
      throw new IllegalArgumentException("No proposed course types found!");
    }

    return foundCourseTypes;
  }

  @Transactional
  public void updateCourseTypeApproval(String userToken, Integer aStaffId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("OWNER"), "Only the owner can approve course types");

    CourseType courseType = courseTypeRepository.findById(aStaffId).orElse(null);

    Validate.notNull(courseType, "Course Type does not exist");
    Validate.isTrue(!courseType.getRejected(), "Course Type has already been rejected");

    courseType.setApproved(!courseType.getApproved());
    courseTypeRepository.save(courseType);
  }

  @Transactional
  public void updateCourseTypeRejection(String userToken, Integer aStaffId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("OWNER"), "Only the owner can reject course types");

    CourseType courseType = courseTypeRepository.findById(aStaffId).orElse(null);

    Validate.notNull(courseType, "Course Type does not exist");
    Validate.isTrue(courseType.getApproved(), "Course Type has already been approved");

    courseType.setRejected(!courseType.getRejected());
    courseTypeRepository.save(courseType);
  }

  @Transactional
  public void deleteCourseType(String userToken, Integer aStaffId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("OWNER"), "Only the owner can delete course types");

    CourseType courseType = courseTypeRepository.findById(aStaffId).orElse(null);
    Validate.notNull(courseType, "Course Type does not exist");

    courseTypeRepository.delete(courseType);
  }
}
