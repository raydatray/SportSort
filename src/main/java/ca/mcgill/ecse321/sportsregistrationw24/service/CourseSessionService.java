package ca.mcgill.ecse321.sportsregistrationw24.service;

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

@Service
public class CourseSessionService {

    @Autowired
    private CourseSessionRepository courseSessionRepository;

    @Transactional
    //Use when you are creating a singular courseSession (Course Offerings where you know for a fact will only have one associated session)
    public CourseSession createCourseSession (Date aDate, Time aStartTime, Time aEndTime, CourseOffering aCourseOffering) {
        CourseSession newCourseSession = new CourseSession();

        newCourseSession.setDate(aDate);
        newCourseSession.setStartTime(aStartTime);
        newCourseSession.setEndTime(aEndTime);
        newCourseSession.setCourseOffering(aCourseOffering);

        courseSessionRepository.save(newCourseSession);

        return newCourseSession;
    }

    @Transactional
    //Use when you are creating courseSessions from a courseOffering with recurring sessions
    public ArrayList<CourseSession> createCourseSessions (Time aStartTime, Time aEndTime, CourseOffering aCourseOffering) {
        LocalDate startDate = aCourseOffering.getStartDate().toLocalDate();
        LocalDate endDate = aCourseOffering.getEndDate().toLocalDate();
        ArrayList<DayOfWeek> daysOffered = aCourseOffering.getDaysOffered();

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
            newCourseSession.setStartTime(aStartTime);
            newCourseSession.setEndTime(aEndTime);
            newCourseSession.setCourseOffering(aCourseOffering);

            generatedCourseSessions.add(newCourseSession);

            courseSessionRepository.save(newCourseSession);
        }

        return generatedCourseSessions;
    }

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
    public ArrayList<CourseSession> getCourseSessionsByCourseOffering (CourseOffering aCourseOffering) {
        Iterable<CourseSession> foundCourseSessions = courseSessionRepository.findByCourseOffering(aCourseOffering).orElse(null);

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
    public void deleteCourseSessionsByCourseOffering (CourseOffering aCourseOffering) {
        Iterable<CourseSession> foundCourseSessions = courseSessionRepository.findByCourseOffering(aCourseOffering).orElse(null);

        if (foundCourseSessions == null) {
            return;
        }

        for (CourseSession foundCourseSession : foundCourseSessions) {
            courseSessionRepository.deleteById(foundCourseSession.getId());
        }
    }
}
