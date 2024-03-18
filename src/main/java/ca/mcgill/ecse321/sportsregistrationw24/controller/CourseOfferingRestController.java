package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.courseOffering.CourseOfferingDto;
import ca.mcgill.ecse321.sportsregistrationw24.dto.courseOffering.CourseOfferingCO;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.service.CourseOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


@CrossOrigin(origins = "*")
@RestController
public class CourseOfferingRestController {

  @Autowired
  private CourseOfferingService service;
  @PostMapping(value = {
    "/courseOfferings/create",
    "/courseOfferings/create/"
  })
  public ResponseEntity<?> createCourseOffering(@RequestBody CourseOfferingCO courseOfferingCO) {
    try {
      Date startDate = courseOfferingCO.getStartDate();
      Date endDate = courseOfferingCO.getEndDate();
      List<DayOfWeek> daysOffered = courseOfferingCO.getDaysOffered();
      String instructorToken = courseOfferingCO.getInstructorToken();
      Integer roomId = courseOfferingCO.getRoomId();

      service.createCourseOffering(startDate, endDate, daysOffered, instructorToken, roomId);
      return ResponseEntity.ok().body("Course offering created successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/courseOfferings/get",
    "/courseOfferings/get/"
  })
  public ResponseEntity<?> getCourseOffering(@RequestParam Integer id, @RequestHeader String userToken) {
    try {
      CourseOffering courseOffering = service.getCourseOfferingById(id, userToken);
      return ResponseEntity.ok().body(convertToDto(courseOffering));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/courseOfferings/getAll",
    "/courseOfferings/getAll/"
  })
  public ResponseEntity<?> getAllCourseOfferings(@RequestHeader String userToken) {
    try {
      List<CourseOfferingDto> courseOfferingDtos = new ArrayList<>();
      for (CourseOffering courseOffering : service.getAllCourseOfferings(userToken)) {
        courseOfferingDtos.add(convertToDto(courseOffering));
      }
      return ResponseEntity.ok().body(courseOfferingDtos);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }


  @DeleteMapping(value = {
    "/courseOfferings/delete",
    "/courseOfferings/delete/"
  })
  public ResponseEntity<?> deleteCourseOffering(@RequestParam Integer id, @RequestHeader String userToken) {
    try {
      service.deleteCourseOffering(id, userToken);
      return ResponseEntity.ok().body("Course offering deleted successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  private CourseOfferingDto convertToDto(CourseOffering courseOffering) {
    if (courseOffering == null) {
      throw new IllegalArgumentException("There is no such course offering!");
    }
    return new CourseOfferingDto(courseOffering.getStartDate(),
      courseOffering.getEndDate(), courseOffering.getDaysOffered(), courseOffering.getInstructorAccount(), courseOffering.getRoom());
    }
}
