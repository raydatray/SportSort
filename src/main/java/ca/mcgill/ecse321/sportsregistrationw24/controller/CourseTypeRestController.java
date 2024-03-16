package ca.mcgill.ecse321.sportsregistrationw24.controller;
import ca.mcgill.ecse321.sportsregistrationw24.dto.courseType.CourseTypeCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.courseType.CourseTypeDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import ca.mcgill.ecse321.sportsregistrationw24.service.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CourseTypeRestController {
  @Autowired
  private CourseTypeService service;
  @PostMapping(value = {
    "/courseTypes/create",
    "/courseTypes/create/"
  })
  public ResponseEntity<?> createCourseType(@RequestBody CourseTypeCO courseTypeCO){
    try {
      String aCourseName = courseTypeCO.getCourseName();

      service.createCourseType(aCourseName);
      return ResponseEntity.ok().body("Course type created succesfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/courseTypes/get",
    "/courseTypes/get/"
  })
  public ResponseEntity<?> getCourseType(@RequestParam Integer aId) {
    try {
      CourseType courseType = service.getCourseType(aId);
      return ResponseEntity.ok().body(convertToDto(courseType));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/courseTypes/getAll",
    "/courseTypes/getAll/"
  })
  public ResponseEntity<?> getAllCourseTypes() {
    try {
      List<CourseTypeDto> courseTypeDtos = new ArrayList<>();
      for (CourseType courseType : service.getAllCourseTypes()) {
        courseTypeDtos.add(convertToDto(courseType));
      }
      return ResponseEntity.ok().body(courseTypeDtos);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {
    "/courseOfferings/update",
    "/courseOfferings/update/"
  })

  public ResponseEntity<?> updateCourseType(@RequestBody CourseTypeDto courseTypeDto, @RequestParam Integer aId, boolean approval){
    try {
      boolean approved = courseTypeDto.getApprovalStatus();

      service.updateCourseTypeApproval(aId, approval);
      return ResponseEntity.ok().body("Course type approval updated successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {
    "/courseTypes/delete",
    "/courseTypes/delete/"
  })
  public ResponseEntity<?> deleteCourseType(@RequestParam Integer id) {
    try {
      service.deleteCourseType(id);
      return ResponseEntity.ok().body("Course type deleted successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  private CourseTypeDto convertToDto(CourseType courseType) {
    if (courseType== null) {
      throw new IllegalArgumentException("There is no such course type!");
    }
    return new CourseTypeDto(courseType.getCourseName(), courseType.getApproved());
  }
}
