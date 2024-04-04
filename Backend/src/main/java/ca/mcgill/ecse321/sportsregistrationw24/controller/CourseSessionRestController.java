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

import java.sql.Date;
import java.sql.Time;
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
      service.createCourseSession(userToken, courseSessionCO.getDate(), courseSessionCO.getStartTime(), courseSessionCO.getEndTime(), courseSessionCO.getCourseOfferingId());
      return ResponseEntity.status(HttpStatus.CREATED).body("Course session created successfully");
    } catch (Exception e) {
      return ResponseEntity. badRequest().body(e.getMessage());
    }
  }

  @PostMapping(value = {"courseSession/createSessions"})
  public ResponseEntity<?> createCourseSessions(@RequestHeader String userToken, @RequestBody multipleClassSessionsCO courseSessionsCO) {
     try {
       service.createCourseSessions(userToken, courseSessionsCO.getDayTimeMapping(), courseSessionsCO.getCourseOfferingId());
       return ResponseEntity.status(HttpStatus.CREATED).body("Course sessions created successfully");
     } catch (Exception e) {
       return ResponseEntity.badRequest().body(e.getMessage());
     }
  }

  @GetMapping(value = {"courseSession/getAllSessions"})
  //Owner ONLY
  public ResponseEntity<?> getAllSessions(@RequestHeader String userToken,
                                          @RequestParam(required = false) Date lD,
                                          @RequestParam(required = false) Date uD,
                                          @RequestParam(required = false) Time lT,
                                          @RequestParam(required = false) Time uT,
                                          @RequestParam(required = false) Integer iId
                                          ){
    List<CourseSession> allCourseSessions = service.getAllCourseSessions(userToken, lD, uD, lT, uT, iId);
    List<CourseSessionDTO> courseSessionDTOS = new ArrayList<>();

    for (CourseSession courseSession : allCourseSessions) {
      courseSessionDTOS.add(new CourseSessionDTO(courseSession));
    }

    return ResponseEntity.ok().body(courseSessionDTOS);
  }

  /**
  public ResponseEntity<?> getSession(@RequestParam Integer courseSessionId) {
    try {
      CourseSession courseSession = service.getCourseSession(courseSessionId);
      return ResponseEntity.ok().body(new CourseSessionDTO(courseSession));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }
   **/

  @GetMapping(value = {"courseSession/getSessionsByOffering"})
  public ResponseEntity<?> getSessionsByOffering(@RequestParam Integer courseOfferingId) {
    try {
      List<CourseSession> allCourseSessions = service.getCourseSessionsByCourseOfferingID(courseOfferingId);
      List<CourseSessionDTO> courseSessionDTOS = new ArrayList<>();

      for (CourseSession courseSession : allCourseSessions) {
        courseSessionDTOS.add(new CourseSessionDTO(courseSession));
      }

      return ResponseEntity.ok().body(courseSessionDTOS);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {"courseSession/deleteSession"})
  public ResponseEntity<?> deleteSession(@RequestHeader String userToken, @RequestParam Integer sessionId) {
    service.deleteCourseSessionByID(userToken, sessionId);
    return ResponseEntity.ok().body("Course Session deleted successfully");
  }

  @DeleteMapping(value = {"courseSession/deleteSessionsByOffering"})
  public ResponseEntity<?> deleteSessionsByOffering(@RequestHeader String userToken, @RequestParam Integer courseOfferingId) {
    try {
      service.deleteCourseSessionsByCourseOfferingID(userToken, courseOfferingId);
      return ResponseEntity.ok().body("Course Sessions deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
