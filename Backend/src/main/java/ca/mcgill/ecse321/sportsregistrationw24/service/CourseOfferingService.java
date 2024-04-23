package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.RoomRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;

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
  public void createCourseOffering(String userToken, Date aStartDate, Date aEndDate, Integer aPrice, List<DayOfWeek> aDaysOffered, Integer aRoomId, Integer aCourseTypeId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (!user.getUserType().equals("INSTRUCTOR")) {
      throw new IllegalArgumentException("Only instructors can create course offerings!");
    }

    if (aEndDate.before(aStartDate)) {
      throw new IllegalArgumentException("End date must be after start date!");
    }

    if (aDaysOffered.isEmpty()) {
      throw new IllegalArgumentException("Course must be offered at least one day a week!");
    }

    Room foundRoom = roomRepository.findById(aRoomId).orElse(null);

    if (foundRoom == null) {
      throw new IllegalArgumentException("Room not found!");
    }

    CourseType foundCourseType = courseTypeRepository.findById(aCourseTypeId).orElse(null);

    if (foundCourseType == null) {
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

    if (foundCourseOffering == null) {
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
                                                    Integer highPrice,
                                                    Time lowTime, Time highTime,
                                                    List<DayOfWeek> daysOffered,
                                                    List<Integer> courseTypeIds,
                                                    List<Integer> roomIds,
                                                    List<Integer> instructorIds) {

    List<CourseType> foundTypes = new ArrayList<>();
    if (courseTypeIds != null) {
      for (Integer courseTypeId : courseTypeIds) {
        CourseType foundType = courseTypeRepository.findById(courseTypeId).orElse(null);
        if (foundType == null) {
          throw new IllegalArgumentException("Course Type not found!");
        }
        foundTypes.add(foundType);
      }
    }

    List<Room> foundRooms = new ArrayList<>();
    if (roomIds != null) {
      for (Integer roomId : roomIds) {
        Room foundRoom = roomRepository.findById(roomId).orElse(null);
        if (foundRoom == null) {
          throw new IllegalArgumentException("Room not found!");
        }
        foundRooms.add(foundRoom);
      }
    }

    List<InstructorAccount> foundInstructors = new ArrayList<>();
    if (instructorIds != null) {
      for (Integer instructorId : instructorIds) {
        InstructorAccount foundInstructor = (InstructorAccount) userAccountRepository.findById(instructorId).orElse(null);
        if (foundInstructor == null) {
          throw new IllegalArgumentException("Instructor not found!");
        }
        foundInstructors.add(foundInstructor);
      }
    }


    List<CourseOffering> foundOfferings = courseOfferingRepository.findCourseOfferingsByFilters(lowDate, highDate, lowTime, highTime, highPrice, foundTypes, daysOffered, foundRooms, foundInstructors).orElse(null);

    if (foundOfferings == null) {
      throw new IllegalArgumentException("No course offerings found with the provided information!");
    }

    return foundOfferings;
  }

  @Transactional
  public void deleteCourseOffering(String userToken, Integer aId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    if (user.getUserType().equals("CUSTOMER")) {
      throw new IllegalArgumentException("Customers cannot delete course offerings!");
    }

    CourseOffering courseOffering = courseOfferingRepository.findById(aId).orElse(null);

    if (courseOffering == null) {
      throw new IllegalArgumentException("Course Offering does not exist!");
    }

    courseOfferingRepository.delete(courseOffering);
  }
}
