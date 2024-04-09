package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

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
  public void createCourseOffering(String userToken, Date aStartDate, Date aEndDate, Integer aPrice, List<DayOfWeek> aDaysOffered, Integer aRoomId, Integer aCourseTypeId){
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("INSTRUCTOR")){
      throw new IllegalArgumentException("Only instructors can create course offerings!");
    }

    if (aEndDate.before(aStartDate)) {
      throw new IllegalArgumentException("End date must be after start date!");
    }

    if (aDaysOffered.isEmpty()) {
      throw new IllegalArgumentException("Course must be offered at least one day a week!");
    }

    Room foundRoom = roomRepository.findById(aRoomId).orElse(null);

    if(foundRoom == null){
      throw new IllegalArgumentException("Room not found!");
    }

    CourseType foundCourseType = courseTypeRepository.findById(aCourseTypeId).orElse(null);

    if(foundCourseType == null){
      throw new IllegalArgumentException("Course Type not found!");
    }

    CourseOffering courseOffering = new CourseOffering();

    courseOffering.setStartDate(aStartDate);
    courseOffering.setEndDate(aEndDate);
    courseOffering.setPrice(aPrice);
    courseOffering.setDaysOffered(aDaysOffered);
    courseOffering.setInstructorAccount((InstructorAccount) user);
    courseOffering.setRoom(foundRoom);
    courseOffering.setCourseType(foundCourseType);

    courseOfferingRepository.save(courseOffering);
  }

  //Do we need this?
  @Deprecated
  @Transactional
  public CourseOffering getCourseOfferingById(String userToken, Integer aId) {
    CourseOffering foundCourseOffering = courseOfferingRepository.findById(aId).orElse(null);

    if(foundCourseOffering == null){
      throw new IllegalArgumentException("Course Offering not found!");
    }

    return foundCourseOffering;
  }

  @Transactional
  public List<CourseOffering> getCourseOfferingsByInstructor(String userToken) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("INSTRUCTOR")) {
      throw new IllegalArgumentException("Only instructors can view their course offerings!");
    }

    List<CourseOffering> foundOfferings = courseOfferingRepository.findByInstructorAccount((InstructorAccount) user).orElse(null);

    if (foundOfferings == null) {
      throw new IllegalArgumentException("No course offerings found for this instructor!");
    }

    return foundOfferings;
  }

  @Transactional
  public List<CourseOffering> getAllCourseOfferings(Date lowDate, Date highDate,
                                                    Integer lowPrice, Integer highPrice,
                                                    Time lowTime, Time highTime,
                                                    List<DayOfWeek> daysOffered,
                                                    Integer courseTypeId,
                                                    Integer roomId,
                                                    Integer instructorId){
    CourseType courseType = null;
    if (courseTypeId != null) {
      courseType = courseTypeRepository.findById(courseTypeId).orElse(null);
      if (courseType == null) {
        throw new IllegalArgumentException("Course Type not found!");
      }
    }

    Room room = null;
    if (roomId != null) {
      room = roomRepository.findById(roomId).orElse(null);
      if (room == null) {
        throw new IllegalArgumentException("Room not found!");
      }
    }

    InstructorAccount instructor = null;
    if (instructorId != null) {
      instructor = (InstructorAccount) userAccountRepository.findById(instructorId).orElse(null);
      if (instructor == null) {
        throw new IllegalArgumentException("Instructor not found!");
      }
    }

    List<CourseOffering> foundOfferings = courseOfferingRepository.findCourseOfferingsByFilters(lowDate, highDate, lowTime, highTime, lowPrice, highPrice, courseType, daysOffered, room, instructor).orElse(null);

    if (foundOfferings == null) {
      throw new IllegalArgumentException("No course offerings found with the provided information!");
    }

    return foundOfferings;
  }

  @Transactional
  public void deleteCourseOffering(String userToken, Integer aId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (user.getUserType().equals("CUSTOMER")){
      throw new IllegalArgumentException("Customers cannot delete course offerings!");
    }

    CourseOffering courseOffering = courseOfferingRepository.findById(aId).orElse(null);

    if (courseOffering == null) {
      throw new IllegalArgumentException("Course Offering does not exist!");
    }

    courseOfferingRepository.delete(courseOffering);
  }
}
