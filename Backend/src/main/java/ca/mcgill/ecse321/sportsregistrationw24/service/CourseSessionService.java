package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.*;
import ca.mcgill.ecse321.sportsregistrationw24.model.*;

import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;

import java.time.DayOfWeek;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities.getUserFromToken;

@Service
public class CourseSessionService {
    @Autowired
    private CourseTypeRepository courseTypeRepository;
    @Autowired
    private CourseSessionRepository courseSessionRepository;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Transactional
    //Use when you are creating a singular courseSession (Course Offerings where you know for a fact will only have one associated session)
    public void createCourseSession (String userToken, Date aDate, Time aStartTime, Time aEndTime, Integer aCourseOfferingId) {
        UserAccount user = getUserFromToken(userAccountRepository, userToken);

        if (!user.getUserType().equals("INSTRUCTOR")) {
            throw new IllegalArgumentException("Only instructors can create course sessions");
        }

        CourseOffering courseOffering = courseOfferingRepository.findById(aCourseOfferingId).orElse(null);

        if (courseOffering == null) {
            throw new IllegalArgumentException("Course Offering not found");
        }

        if (!courseOffering.getInstructorAccount().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Instructor does not have permission to create course session for this course offering");
        }

        // NULL POINTER CHECKS
        if (aDate == null) {
            throw new IllegalArgumentException("Date field cannot be null");
        } else if (aStartTime == null) {
            throw new IllegalArgumentException("Start time field cannot be null");
        } else if (aEndTime == null) {
            throw new IllegalArgumentException("End time field cannot be null");
        }
        // START AND END TIME CHECKS
        if (aStartTime.compareTo(aEndTime) == 0) {
            throw new IllegalArgumentException("Session start time cannot be equal to end time");
        } else if (aStartTime.compareTo(aEndTime) > 0) {
            throw new IllegalArgumentException("Session start time cannot be after session end time");
        }

        // DATE CHECKS
        if (aDate.toLocalDate().isBefore(courseOffering.getStartDate().toLocalDate())) {
            throw new IllegalArgumentException("Date of course session cannot be before start date of course offering");
        } else if (aDate.toLocalDate().isAfter(courseOffering.getEndDate().toLocalDate())) {
            throw new IllegalArgumentException("Date of course session cannot be after end date of course offering");
        }

        CourseSession newCourseSession = new CourseSession();

        newCourseSession.setDate(aDate);
        newCourseSession.setStartTime(aStartTime);
        newCourseSession.setEndTime(aEndTime);
        newCourseSession.setCourseOffering(courseOffering);

        courseSessionRepository.save(newCourseSession);
    }

    @Transactional
    //Use when you are creating courseSessions from a courseOffering with recurring sessions
    public void createCourseSessions (String userToken, HashMap<DayOfWeek, ArrayList<Time>> dayTimeMapping, Integer aCourseOfferingID) {
        UserAccount user = getUserFromToken(userAccountRepository, userToken);

        if (!user.getUserType().equals("INSTRUCTOR")) {
            throw new IllegalArgumentException("Only instructors can create course sessions");
        }

        CourseOffering courseOffering = courseOfferingRepository.findById(aCourseOfferingID).orElse(null);

        if (courseOffering == null) {
            throw new IllegalArgumentException("Course Offering not found");
        }

        if (!courseOffering.getInstructorAccount().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Instructor does not have permission to create course session for this course offering");
        }


        if (dayTimeMapping == null) {
            throw new IllegalArgumentException("Day time mapping field cannot be null");
        }


        LocalDate startDate = courseOffering.getStartDate().toLocalDate();
        LocalDate endDate = courseOffering.getEndDate().toLocalDate();
        ArrayList<DayOfWeek> daysOffered = courseOffering.getDaysOffered();

        LocalDate generatedDate = startDate;
        ArrayList<Date> sessionDates = new ArrayList<Date>();

        while (!(generatedDate.isAfter(endDate))) {
            if (daysOffered.contains(generatedDate.getDayOfWeek())) {
                sessionDates.add(Date.valueOf(generatedDate));
            }
            generatedDate = generatedDate.plusDays(1);
        }

        for (Date sessionDate : sessionDates) {
            CourseSession newCourseSession = new CourseSession();

            newCourseSession.setDate(sessionDate);
            newCourseSession.setStartTime(dayTimeMapping.get(sessionDate.toLocalDate().getDayOfWeek()).get(0));
            newCourseSession.setEndTime(dayTimeMapping.get(sessionDate.toLocalDate().getDayOfWeek()).get(1));
            newCourseSession.setCourseOffering(courseOffering);

            courseSessionRepository.save(newCourseSession);
        }

    }

    @Deprecated
    //YOU SHOULD NOT USE THIS WE DO NOT ALLOW UPDATE OPERATIONS
    @Transactional
    public void updateCourseSession (Integer aID, Date aDate, Time aStartTime, Time aEndTime, CourseOffering aCourseOffering) {
        CourseSession courseSessionToUpdate = courseSessionRepository.findById(aID).orElse(null);

        if (courseSessionToUpdate == null) {
            throw new IllegalArgumentException("Course Session not found");
        }

        courseSessionToUpdate.setDate(aDate);
        courseSessionToUpdate.setStartTime(aStartTime);
        courseSessionToUpdate.setEndTime(aEndTime);
        courseSessionToUpdate.setCourseOffering(aCourseOffering);

        courseSessionRepository.save(courseSessionToUpdate);
    }

    @Transactional
    public List<CourseSession> getAllCourseSessions (String userToken,
                                                     Date lowDate, Date highDate,
                                                     Time lowTime, Time highTime,
                                                     List<DayOfWeek> daysOffered,
                                                     Integer courseTypeId,
                                                     Integer courseOfferingId,
                                                     Integer roomId,
                                                     Integer instructorId) {
        UserAccount user = getUserFromToken(userAccountRepository, userToken);

        if (!user.getUserType().equals("OWNER")) {
            throw new IllegalArgumentException("Only owners can view all course sessions");
        }

        InstructorAccount instructor = null;
        if (instructorId != null) {
            instructor = (InstructorAccount) userAccountRepository.findById(instructorId).orElse(null);
            if (instructor == null) {
                throw new IllegalArgumentException("Instructor not found");
            }
        }

        CourseType courseType = null;
        if (courseTypeId != null) {
            courseType = courseTypeRepository.findById(courseTypeId).orElse(null);
            if (courseType == null) {
                throw new IllegalArgumentException("Course Type not found");
            }
        }

        CourseOffering c = null;
        if (courseOfferingId != null) {
            c = courseOfferingRepository.findById(courseOfferingId).orElse(null);
            if (c == null) {
                throw new IllegalArgumentException("Course Offering not found");
            }
        }

        Room room = null;
        if (roomId != null) {
            room = roomRepository.findById(roomId).orElse(null);
            if (room == null) {
                throw new IllegalArgumentException("Room not found");
            }
        }


        List<CourseSession> foundCourseSessions = courseSessionRepository.findCourseSessionsByFilters(lowDate, highDate, lowTime, highTime, courseType, daysOffered, c, room, instructor).orElse(null);

        if(foundCourseSessions == null){
            throw new IllegalArgumentException("No course sessions found with the provided information");
        }
        return foundCourseSessions;
    }

    // when would we ever use this?
    @Deprecated
    @Transactional
    public CourseSession getCourseSession(Integer courseSessionId) {
        if (courseSessionId == null) {
            throw new IllegalArgumentException("Course session ID was null");
        }

        if (courseSessionId < 1) {
            throw new IllegalArgumentException("Course session ID is invalid");
        }

        CourseSession courseSession = courseSessionRepository.findById(courseSessionId).orElse(null);

        if (courseSession == null) {
            throw new IllegalArgumentException("Course session was not found");
        }

        return courseSession;
    }

    @Transactional
    public List<CourseSession> getCourseSessionsByCourseOfferingID (Integer courseOfferingID) {
        CourseOffering foundCourseOffering = courseOfferingRepository.findById(courseOfferingID).orElse(null);

        if (foundCourseOffering == null) {
            throw new IllegalArgumentException("Course Offering not found");
        }

        Iterable<CourseSession> foundCourseSessions = courseSessionRepository.findByCourseOffering(foundCourseOffering).orElse(null);

        if (foundCourseSessions == null) {
            return null;
        }

        return Utilities.iterableToArrayList(foundCourseSessions);
    }

    @Transactional
    public void deleteCourseSessionByID (String userToken, Integer aID) {
        UserAccount user = getUserFromToken(userAccountRepository, userToken);

        if (user.getUserType().equals("CUSTOMER")) { //when would this ever happen??!
            throw new IllegalArgumentException("Customers cannot delete course sessions");
        }

        CourseSession foundCourseSession = courseSessionRepository.findById(aID).orElse(null);

        if (foundCourseSession == null) {
            throw new IllegalArgumentException("Course Session not found");
        }

        if (user.getUserType().equals("INSTRUCTOR")) {
            if (!foundCourseSession.getCourseOffering().getInstructorAccount().getId().equals(user.getId())) {
                throw new IllegalArgumentException("Instructor does not have permission to delete this course session");
            }

        }

        courseSessionRepository.deleteById(aID);
    }


    @Transactional
    public void deleteCourseSessionsByCourseOfferingID (String userToken, Integer courseOfferingID) {
        UserAccount user = getUserFromToken(userAccountRepository, userToken);

        if (user.getUserType().equals("CUSTOMER")) {
            throw new IllegalArgumentException("Customers cannot delete course sessions");
        }

        CourseOffering foundCourseOffering = courseOfferingRepository.findById(courseOfferingID).orElse(null);

        if (foundCourseOffering == null) {
            throw new IllegalArgumentException("Course Offering not found");
        }

        if (user.getUserType().equals("INSTRUCTOR")) {
            if (!foundCourseOffering.getInstructorAccount().getId().equals(user.getId())) {
                throw new IllegalArgumentException("Instructor does not have permission to delete course sessions for this course offering");
            }
        }

        Iterable<CourseSession> foundCourseSessions = courseSessionRepository.findByCourseOffering(foundCourseOffering).orElse(null);

        if (foundCourseSessions == null) {
            throw new IllegalArgumentException("No course sessions found for this course offering");
        }

        for (CourseSession foundCourseSession : foundCourseSessions) {
            courseSessionRepository.deleteById(foundCourseSession.getId());
        }
    }
}
