package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.courseSession.CourseSessionDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseSession;
import ca.mcgill.ecse321.sportsregistrationw24.service.CourseSessionService;

import ca.mcgill.ecse321.sportsregistrationw24.dto.courseSession.singleCourseSessionCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.courseSession.multipleClassSessionsCO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CourseSessionRestController {
  @Autowired
  private CourseSessionService service;

  @PostMapping(value = {
    "courseSession/createSession",
    "courseSession/createSession/"
  })
  public ResponseEntity<?> createCourseSession(@RequestBody singleCourseSessionCO courseSessionCO) {
    try {
      CourseSession createdCourseSession = service.createCourseSession(courseSessionCO.getDate(), courseSessionCO.getStartTime(), courseSessionCO.getEndTime(), courseSessionCO.getCourseOfferingId());
      return ResponseEntity.ok().body(new CourseSessionDto(createdCourseSession));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping(value = {
    "courseSession/createSessions",
    "courseSession/createSessions/"
  })
  public ResponseEntity<?> createCourseSessions(@RequestBody multipleClassSessionsCO courseSessionsCO) {
     try {
       ArrayList<CourseSession> createdCourseSessions = service.createCourseSessions(courseSessionsCO.getDayTimeMapping(), courseSessionsCO.getCourseOfferingId());
       List<CourseSessionDto> courseSessionDtos = new ArrayList<>();

       for (CourseSession courseSession : createdCourseSessions) {
         courseSessionDtos.add(new CourseSessionDto(courseSession));
       }

       return ResponseEntity.ok().body(courseSessionDtos);
     } catch (Exception e) {
       return ResponseEntity.badRequest().body(e.getMessage());
     }
  }

  @GetMapping(value = {
    "courseSession/getAllSessions",
    "courseSession/getAllSessions/"
  })
  //Owner ONLY
  public ResponseEntity<?> getAllSessions() {
    ArrayList<CourseSession> allCourseSessions = service.getAllCourseSessions();
    List<CourseSessionDto> courseSessionDtos = new ArrayList<>();

    for (CourseSession courseSession : allCourseSessions) {
      courseSessionDtos.add(new CourseSessionDto(courseSession));
    }

    return ResponseEntity.ok().body(courseSessionDtos);
  }

  @GetMapping(value = {
          "courseSession/getSessionsByOffering",
          "courseSession/getSessionsByOffering/"
  })
  public ResponseEntity<?> getSessionsByOffering(@RequestParam Integer courseOfferingId) {
    try {
      ArrayList<CourseSession> allCourseSessions = service.getCourseSessionsByCourseOfferingID(courseOfferingId);
      List<CourseSessionDto> courseSessionDtos = new ArrayList<>();

      for (CourseSession courseSession : allCourseSessions) {
        courseSessionDtos.add(new CourseSessionDto(courseSession));
      }

      return ResponseEntity.ok().body(courseSessionDtos);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {
    "courseSession/deleteSession",
    "courseSession/deleteSession/"
  })
  public ResponseEntity<?> deleteSession(@RequestParam Integer sessionId) {
    service.deleteCourseSessionByID(sessionId);
    return ResponseEntity.ok().body("Course Session deleted successfully");
  }

  @DeleteMapping(value = {
    "courseSession/deleteSessionsByOffering",
    "courseSession/deleteSessionsByOffering/"
  })
  public ResponseEntity<?> deleteSessionsByOffering(@RequestParam Integer courseOfferingId) {
    try {
      service.deleteCourseSessionsByCourseOfferingID(courseOfferingId);
      return ResponseEntity.ok().body("Course Sessions deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
