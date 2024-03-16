package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseTypeService {
  @Autowired
  private CourseTypeRepository courseTypeRepository;

  @Transactional
  public CourseType createCourseType (String aCourseName){
    CourseType courseType = new CourseType();
    courseType.setCourseName(aCourseName);
    // created course type initially requires approval
    courseType.setApproved(false);
    courseTypeRepository.save(courseType);
    return courseType;
  }

  @Transactional
  public CourseType getCourseType(Integer aId) {
    return courseTypeRepository.findById(aId).orElse(null);
  }

  @Transactional
  public List<CourseType> getAllCourseTypes() {
    return toList(courseTypeRepository.findAll());
  }

  @Transactional
  public void updateCourseTypeApproval(Integer aId, boolean approval) {
    CourseType courseType = getCourseType(aId);
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
  public void deleteCourseType(Integer aId) {
    CourseType courseType = getCourseType(aId);
    if (courseType == null) {
      throw new IllegalArgumentException("Course Type does not exist!");
    }
    courseTypeRepository.delete(courseType);
  }



}
