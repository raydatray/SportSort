package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.CourseSessionDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseSession;
import ca.mcgill.ecse321.sportsregistrationw24.service.CourseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CourseSessionRestController {
        @Autowired
        private CourseSessionService service;

        /**
         * Create a single courseSession or multiple coruseSessions automatically
         *
         */
        @PostMapping(value = {
                "courseSession/create",
                "courseSession/create/"
        })
        public void createCourseSession() {
        }

        @GetMapping(value = {
                "courseSession/getAllSessions",
                "courseSession/getAllSessions/"
        })
        //Owner ONLY
        public List<CourseSessionDto> getAllSessions() {
                Iterable<CourseSession> allCourseSessions = service.getAllCourseSessions();

                List<CourseSessionDto> courseSessionDtos = new ArrayList<>();

                for (CourseSession courseSession : allCourseSessions) {
                        courseSessionDtos.add(new CourseSessionDto(courseSession));
                }

                return courseSessionDtos;
        }

        @GetMapping(value = {
                "courseSession/getSessionsByOffering",
                "courseSession/getSessionsByOffering/"
        })
        public List<CourseSessionDto> getSessionsByOffering(@RequestParam Integer courseOfferingId) {
                Iterable<CourseSession> allCourseSessions = service.getCourseSessionsByCourseOfferingID(courseOfferingId);

                List<CourseSessionDto> courseSessionDtos = new ArrayList<>();

                for (CourseSession courseSession : allCourseSessions) {
                        courseSessionDtos.add(new CourseSessionDto(courseSession));
                }

                return courseSessionDtos;
        }

        @DeleteMapping(value = {
                "courseSession/deleteSession",
                "courseSession/deleteSession/"
        })
        public void deleteSession(@RequestParam Integer sessionId) {
                service.deleteCourseSessionByID(sessionId);
        }

        @DeleteMapping(value = {
                "courseSession/deleteSessionsByOffering",
                "courseSession/deleteSessionsByOffering/"
        })
        public void deleteSessionsByOffering(@RequestParam Integer courseOfferingId) {
                service.deleteCourseSessionsByCourseOfferingID(courseOfferingId);
        }
}
