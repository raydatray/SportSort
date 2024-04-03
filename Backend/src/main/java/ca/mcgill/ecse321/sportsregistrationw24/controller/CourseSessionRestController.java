package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.CourseSession.CourseSessionDTO;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseSession;
import ca.mcgill.ecse321.sportsregistrationw24.service.CourseSessionService;

import ca.mcgill.ecse321.sportsregistrationw24.dto.CourseSession.singleCourseSessionCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.CourseSession.multipleClassSessionsCO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CourseSessionRestController {
  @Autowired
  private CourseSessionService service;

  @PostMapping(value = {"courseSessions/createSession"})
  public ResponseEntity<?> createCourseSession(@RequestHeader String userToken, @RequestBody singleCourseSessionCO courseSessionCO) {
    try {
      service.createCourseSession(courseSessionCO.getDate(), courseSessionCO.getStartTime(), courseSessionCO.getEndTime(), courseSessionCO.getCourseOfferingId());
      return ResponseEntity.status(HttpStatus.CREATED).body("Course session created successfully");
    } catch (Exception e) {
      return ResponseEntity. badRequest().body(e.getMessage());
    }
  }

  @PostMapping(value = {"courseSession/createSessions"})
  public ResponseEntity<?> createCourseSessions(@RequestBody multipleClassSessionsCO courseSessionsCO) {
     try {
       service.createCourseSessions(courseSessionsCO.getDayTimeMapping(), courseSessionsCO.getCourseOfferingId());
       List<CourseSessionDTO> courseSessionDTOS = new ArrayList<>();

       for (CourseSession courseSession : createdCourseSessions) {
         courseSessionDTOS.add(new CourseSessionDTO(courseSession));
       }

       return ResponseEntity.status(HttpStatus.CREATED).body("Course sessions created successfully");
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
    List<CourseSessionDTO> courseSessionDTOS = new ArrayList<>();

    for (CourseSession courseSession : allCourseSessions) {
      courseSessionDTOS.add(new CourseSessionDTO(courseSession));
    }

    return ResponseEntity.ok().body(courseSessionDTOS);
  }

  @GetMapping(value = {
    "courseSession/getSession",
    "courseSession/getSession/"
  })
  public ResponseEntity<?> getSession(@RequestParam Integer courseSessionId) {
    try {
      CourseSession courseSession = service.getCourseSession(courseSessionId);
      return ResponseEntity.ok().body(new CourseSessionDTO(courseSession));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

  }

  @GetMapping(value = {
          "courseSession/getSessionsByOffering",
          "courseSession/getSessionsByOffering/"
  })
  public ResponseEntity<?> getSessionsByOffering(@RequestParam Integer courseOfferingId) {
    try {
      ArrayList<CourseSession> allCourseSessions = service.getCourseSessionsByCourseOfferingID(courseOfferingId);
      List<CourseSessionDTO> courseSessionDTOS = new ArrayList<>();

      for (CourseSession courseSession : allCourseSessions) {
        courseSessionDTOS.add(new CourseSessionDTO(courseSession));
      }

      return ResponseEntity.ok().body(courseSessionDTOS);
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
