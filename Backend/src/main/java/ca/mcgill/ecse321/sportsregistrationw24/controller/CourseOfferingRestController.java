package ca.mcgill.ecse321.sportsregistrationw24.controller;


import ca.mcgill.ecse321.sportsregistrationw24.dto.CourseOffering.CourseOfferingCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.CourseOffering.CourseOfferingDTO;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.service.CourseOfferingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;
import java.sql.Date;


@CrossOrigin(origins = "*")
@RestController
public class CourseOfferingRestController {
  @Autowired
  private CourseOfferingService service;
  @PostMapping(value = {"/courseOfferings/create"})
  public ResponseEntity<?> createCourseOffering(@RequestHeader String userToken, @RequestBody CourseOfferingCO courseOfferingCO) {
    try {
      Date startDate = courseOfferingCO.getStartDate();
      Date endDate = courseOfferingCO.getEndDate();
      Integer price = courseOfferingCO.getPrice();
      List<DayOfWeek> daysOffered = courseOfferingCO.getDaysOffered();
      Integer roomId = courseOfferingCO.getRoomId();
      Integer courseTypeId = courseOfferingCO.getCourseTypeId();

      service.createCourseOffering(userToken, startDate, endDate, price, daysOffered, roomId, courseTypeId);
      return ResponseEntity.ok().body("Course offering created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  /**
  public ResponseEntity<?> getCourseOffering(@RequestParam Integer id, @RequestHeader String userToken) {
    try {
      CourseOffering courseOffering = service.getCourseOfferingById(id, userToken);
      return ResponseEntity.ok().body(convertToDto(courseOffering));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
   **/

  @GetMapping(value = {"courseOfferings/getByInstructor"})
  public ResponseEntity<?> getCourseOfferingsByInstructor(@RequestHeader String userToken) {
    try {
      List<CourseOffering> courseOfferings = service.getCourseOfferingsByInstructor(userToken);
      List<CourseOfferingDTO> courseOfferingDTOs = courseOfferings.stream().map(CourseOfferingDTO::new).toList();
      return ResponseEntity.ok().body(courseOfferingDTOs);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {"/courseOfferings/getAll"})
  public ResponseEntity<?> getAllCourseOfferings(@RequestParam(required = false) Date lD,
                                                 @RequestParam(required = false) Date hD,
                                                 @RequestParam(required = false) Integer hP,
                                                 @RequestParam(required = false) Time lT,
                                                 @RequestParam(required = false) Time hT,
                                                 @RequestParam(required = false) List<DayOfWeek> dO,
                                                 @RequestParam(required = false) List<Integer> cT,
                                                 @RequestParam(required = false) List<Integer> rId,
                                                 @RequestParam(required = false) List<Integer> iI){
    try {
      List<CourseOffering> courseOfferings = service.getAllCourseOfferings(lD, hD, hP, lT, hT, dO, cT, rId, iI);
      List<CourseOfferingDTO> courseOfferingDTOs = courseOfferings.stream().map(CourseOfferingDTO::new).toList();

      return ResponseEntity.ok().body(courseOfferingDTOs);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {"/courseOfferings/delete"})
  public ResponseEntity<?> deleteCourseOffering(@RequestHeader String userToken, @RequestParam Integer id) {
    try {
      service.deleteCourseOffering(userToken, id);
      return ResponseEntity.ok().body("Course offering deleted successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
