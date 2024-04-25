package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseTypeRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.RoomRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.UserAccountRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang3.Validate;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;

@Service
public class CourseOfferingService {
  private final CourseOfferingRepository courseOfferingRepository;
  private final CourseTypeRepository courseTypeRepository;
  private final UserAccountRepository userAccountRepository;
  private final RoomRepository roomRepository;

  @Autowired
  public CourseOfferingService(CourseOfferingRepository courseOfferingRepository, CourseTypeRepository courseTypeRepository, UserAccountRepository userAccountRepository, RoomRepository roomRepository) {
    this.courseOfferingRepository = courseOfferingRepository;
    this.courseTypeRepository = courseTypeRepository;
    this.userAccountRepository = userAccountRepository;
    this.roomRepository = roomRepository;
  }

  @Transactional
  public void createCourseOffering(String userToken, String aName, CourseOffering.difficultyLevel aDifficultyLevel, Integer aPrice, Integer aCapacity, Date aStartDate, Date aEndDate, List<DayOfWeek> aDaysOffered, Integer aCourseTypeId) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);

    Validate.isTrue(user.getUserType().equals("INSTRUCTOR"), "Only instructors can create course offerings");
    Validate.notBlank(aName, "Course offering name cannot be empty");
    Validate.notNull(aDifficultyLevel, "Difficulty level cannot be null");
    Validate.isTrue(aPrice > 0, "Price must be above 0");
    Validate.isTrue(aCapacity > 0, "Capacity must be above 0");
    Validate.isTrue(aEndDate.after(aStartDate), "End date must be after start date");
    Validate.notEmpty(aDaysOffered, "Course must be offered at least one day a week");

    CourseType foundCourseType = courseTypeRepository.findById(aCourseTypeId).orElse(null);

    Validate.notNull(foundCourseType, "Course type not found");

    CourseOffering courseOffering = new CourseOffering();

    courseOffering.setName(aName);
    courseOffering.setDifficulty(aDifficultyLevel);
    courseOffering.setPrice(aPrice);
    courseOffering.setCapacity(aCapacity);
    courseOffering.setStartDate(aStartDate);
    courseOffering.setEndDate(aEndDate);
    courseOffering.setPrice(aPrice);
    courseOffering.setDaysOffered(aDaysOffered);
    courseOffering.setInstructorAccount((InstructorAccount) user);
    courseOffering.setCourseType(foundCourseType);

    courseOfferingRepository.save(courseOffering);
  }

  @Transactional
  public List<CourseOffering> getCourseOfferingsByInstructor(String userToken) {
    UserAccount user = getUserFromToken(userAccountRepository, userToken);
    Validate.isTrue(user.getUserType().equals("INSTRUCTOR"), "Only instructors can view their course offerings");

    List<CourseOffering> foundOfferings = courseOfferingRepository.findByInstructorAccount((InstructorAccount) user).orElse(null);

    if (foundOfferings == null) {
      throw new IllegalArgumentException("No course offerings found for this instructor");
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
    Validate.isTrue(!user.getUserType().equals("CUSTOMER"), "Customers cannot delete course offerings!");

    CourseOffering courseOffering = courseOfferingRepository.findById(aId).orElse(null);
    Validate.notNull(courseOffering, "Course Offering not found!");

    courseOfferingRepository.delete(courseOffering);
  }
}
