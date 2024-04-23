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
import java.time.DayOfWeek;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CourseSessionRestController {
  @Autowired
  private CourseSessionService service;

  @PostMapping(value = {"courseSessions/createSingle"})
  public ResponseEntity<?> createCourseSession(@RequestHeader String userToken, @RequestBody singleCourseSessionCO courseSessionCO) {
    try {
      service.createCourseSession(userToken, courseSessionCO.getDate(), courseSessionCO.getStartTime(), courseSessionCO.getEndTime(), courseSessionCO.getCourseOfferingId());
      return ResponseEntity.status(HttpStatus.CREATED).body("Course session created successfully");
    } catch (Exception e) {
      return ResponseEntity. badRequest().body(e.getMessage());
    }
  }

  @PostMapping(value = {"courseSessions/createMultiple"})
  public ResponseEntity<?> createCourseSessions(@RequestHeader String userToken, @RequestBody multipleClassSessionsCO courseSessionsCO) {
     try {
       service.createCourseSessions(userToken, courseSessionsCO.getDayTimeMapping(), courseSessionsCO.getCourseOfferingId());
       return ResponseEntity.status(HttpStatus.CREATED).body("Course sessions created successfully");
     } catch (Exception e) {
       return ResponseEntity.badRequest().body(e.getMessage());
     }
  }

  @GetMapping(value = {"courseSessions/getAll"})
  //Owner ONLY
  public ResponseEntity<?> getAllSessions(@RequestHeader String userToken,
                                          @RequestParam(required = false) Date lD,
                                          @RequestParam(required = false) Date uD,
                                          @RequestParam(required = false) Time lT,
                                          @RequestParam(required = false) Time uT,
                                          @RequestParam(required = false) List<DayOfWeek> d,
                                          @RequestParam(required = false) Integer cTId,
                                          @RequestParam(required = false) Integer cOId,
                                          @RequestParam(required = false) Integer rId,
                                          @RequestParam(required = false) Integer iId
                                          ){
    try {
      List<CourseSession> allCourseSessions = service.getAllCourseSessions(userToken, lD, uD, lT, uT, d, cTId, cOId, rId, iId);
      List<CourseSessionDTO> courseSessionDTOs = allCourseSessions.stream().map(CourseSessionDTO::new).toList();

      return ResponseEntity.ok().body(courseSessionDTOs);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
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

  @GetMapping(value = {"courseSessions/getByOffering"})
  public ResponseEntity<?> getSessionsByOffering(@RequestParam Integer courseOfferingId) {
    try {
      List<CourseSession> allCourseSessions = service.getCourseSessionsByCourseOfferingID(courseOfferingId);
      List<CourseSessionDTO> courseSessionDTOs = allCourseSessions.stream().map(CourseSessionDTO::new).toList();

      return ResponseEntity.ok().body(courseSessionDTOs);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {"courseSessions/delete"})
  public ResponseEntity<?> deleteSession(@RequestHeader String userToken, @RequestParam Integer sessionId) {
    service.deleteCourseSessionByID(userToken, sessionId);
    return ResponseEntity.ok().body("Course Session deleted successfully");
  }

  @DeleteMapping(value = {"courseSessions/deleteByOffering"})
  public ResponseEntity<?> deleteSessionsByOffering(@RequestHeader String userToken, @RequestParam Integer courseOfferingId) {
    try {
      service.deleteCourseSessionsByCourseOfferingID(userToken, courseOfferingId);
      return ResponseEntity.ok().body("Course Sessions deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
