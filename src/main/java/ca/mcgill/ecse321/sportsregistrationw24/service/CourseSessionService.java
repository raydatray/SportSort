package ca.mcgill.ecse321.sportsregistrationw24.service;

import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseOfferingRepository;
import ca.mcgill.ecse321.sportsregistrationw24.dao.CourseSessionRepository;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseSession;

import ca.mcgill.ecse321.sportsregistrationw24.utilities.Utilities;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;

import java.time.DayOfWeek;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class CourseSessionService {

    @Autowired
    private CourseSessionRepository courseSessionRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Transactional
    //Use when you are creating a singular courseSession (Course Offerings where you know for a fact will only have one associated session)
    public CourseSession createCourseSession (Date aDate, Time aStartTime, Time aEndTime, Integer aCourseOfferindId) {
        // NULL POINTER CHECKS
        if (aDate == null) {
            throw new IllegalArgumentException("Date field cannot be null");
        } else if (aStartTime == null) {
            throw new IllegalArgumentException("Start time field cannot be null");
        } else if (aEndTime == null) {
            throw new IllegalArgumentException("End time field cannot be null");
        } else if (aCourseOfferindId == null) {
            throw new IllegalArgumentException("Course offering ID field cannot be null");
        }
        // START AND END TIME CHECKS
        if (aStartTime.compareTo(aEndTime) == 0) {
            throw new IllegalArgumentException("Session start time cannot be equal to end time");
        } else if (aStartTime.compareTo(aEndTime) > 0) {
            throw new IllegalArgumentException("Session start time cannot be after session end time");
        }
        // SESSION DURATION CHECK (CANNOT BE OVER 1 HOUR)
        long sessionDuration = Math.abs(aStartTime.getTime() - aEndTime.getTime());
        long oneHour = 60 * 60 * 1000;
        if (sessionDuration > oneHour) {
            throw new IllegalArgumentException("Course sessions cannot last for over an hour");
        }

        CourseOffering foundCourseOffering = courseOfferingRepository.findById(aCourseOfferindId).orElse(null);

        if (foundCourseOffering == null) {
            throw new IllegalArgumentException("Course Offering not found");
        }
        // DATE CHECKS
        if (aDate.toLocalDate().isBefore(foundCourseOffering.getStartDate().toLocalDate())) {
            throw new IllegalArgumentException("Date of course session cannot be before start date of course offering");
        } else if (aDate.toLocalDate().isAfter(foundCourseOffering.getEndDate().toLocalDate())) {
            throw new IllegalArgumentException("Date of course session cannot be after end date of course offering");
        }

        CourseSession newCourseSession = new CourseSession();

        newCourseSession.setDate(aDate);
        newCourseSession.setStartTime(aStartTime);
        newCourseSession.setEndTime(aEndTime);
        newCourseSession.setCourseOffering(foundCourseOffering);

        courseSessionRepository.save(newCourseSession);

        return newCourseSession;
    }

    @Transactional
    //Use when you are creating courseSessions from a courseOffering with recurring sessions
    public ArrayList<CourseSession> createCourseSessions (HashMap<DayOfWeek, ArrayList<Time>> dayTimeMapping, Integer aCourseOfferingID) {
        CourseOffering foundCourseOffering = courseOfferingRepository.findById(aCourseOfferingID).orElse(null);

        if (foundCourseOffering == null) {
            throw new IllegalArgumentException("Course Offering not found");
        }

        LocalDate startDate = foundCourseOffering.getStartDate().toLocalDate();
        LocalDate endDate = foundCourseOffering.getEndDate().toLocalDate();
        ArrayList<DayOfWeek> daysOffered = foundCourseOffering.getDaysOffered();

        LocalDate generatedDate = startDate;
        ArrayList<Date> sessionDates = new ArrayList<Date>();

        while (!(generatedDate.isAfter(endDate))) {
            if (daysOffered.contains(generatedDate.getDayOfWeek())) {
                sessionDates.add(Date.valueOf(generatedDate));
            }
            generatedDate = generatedDate.plusDays(1);
        }

        ArrayList<CourseSession> generatedCourseSessions = new ArrayList<CourseSession>();

        for (Date sessionDate : sessionDates) {
            CourseSession newCourseSession = new CourseSession();

            newCourseSession.setDate(sessionDate);
            newCourseSession.setStartTime(dayTimeMapping.get(sessionDate.toLocalDate().getDayOfWeek()).get(0));
            newCourseSession.setEndTime(dayTimeMapping.get(sessionDate.toLocalDate().getDayOfWeek()).get(1));
            newCourseSession.setCourseOffering(foundCourseOffering);

            generatedCourseSessions.add(newCourseSession);

            courseSessionRepository.save(newCourseSession);
        }

        return generatedCourseSessions;
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
    public ArrayList<CourseSession> getAllCourseSessions () {
        return Utilities.iterableToArrayList(courseSessionRepository.findAll());
    }

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
    public ArrayList<CourseSession> getCourseSessionsByCourseOfferingID (Integer courseOfferingID) {
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
    public void deleteCourseSessionByID (Integer aID) {
        courseSessionRepository.deleteById(aID);
    }


    @Transactional
    public void deleteCourseSessionsByCourseOfferingID (Integer courseOfferingID) {
        CourseOffering foundCourseOffering = courseOfferingRepository.findById(courseOfferingID).orElse(null);

        if (foundCourseOffering == null) {
            throw new IllegalArgumentException("Course Offering not found");
        }

        Iterable<CourseSession> foundCourseSessions = courseSessionRepository.findByCourseOffering(foundCourseOffering).orElse(null);

        if (foundCourseSessions == null) {
            return;
        }

        for (CourseSession foundCourseSession : foundCourseSessions) {
            courseSessionRepository.deleteById(foundCourseSession.getId());
        }
    }
}
