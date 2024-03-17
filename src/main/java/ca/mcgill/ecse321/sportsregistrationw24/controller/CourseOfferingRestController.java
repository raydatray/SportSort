package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.courseOffering.CourseOfferingDto;
import ca.mcgill.ecse321.sportsregistrationw24.dto.courseOffering.CourseOfferingCO;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import ca.mcgill.ecse321.sportsregistrationw24.service.CourseOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
      Room room = courseOfferingCO.getRoom();
      String userType = courseOfferingCO.getUserType();

      service.createCourseOffering(startDate, endDate, room, userType);
      return ResponseEntity.ok().body("Course offering created successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/courseOfferings/get",
    "/courseOfferings/get/"
  })
  public ResponseEntity<?> getCourseOffering(@RequestParam Integer id, String userEmail) {
    try {
      CourseOffering courseOffering = service.getCourseOfferingById(id, userEmail);
      return ResponseEntity.ok().body(convertToDto(courseOffering));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/courseOfferings/getAll",
    "/courseOfferings/getAll/"
  })
  public ResponseEntity<?> getAllCourseOfferings(String userEmail) {
    try {
      List<CourseOfferingDto> courseOfferingDtos = new ArrayList<>();
      for (CourseOffering courseOffering : service.getAllCourseOfferings(userEmail)) {
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
  public ResponseEntity<?> deleteCourseOffering(@RequestParam Integer id, String userEmail) {
    try {
      service.deleteCourseOffering(id, userEmail);
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
      courseOffering.getEndDate(), courseOffering.getRoom(), courseOffering.getId());
    }
}
