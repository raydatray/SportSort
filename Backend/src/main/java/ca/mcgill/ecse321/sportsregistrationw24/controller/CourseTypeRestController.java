package ca.mcgill.ecse321.sportsregistrationw24.controller;
import ca.mcgill.ecse321.sportsregistrationw24.dto.CourseTypeDTO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.UserAccounts.UserAccountDTO;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseType;
import ca.mcgill.ecse321.sportsregistrationw24.service.CourseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @PostMapping(value = {"/courseTypes/create"})
  public ResponseEntity<?> createCourseType(@RequestHeader String userToken, @RequestParam String courseTypeName){
    try {
      service.createCourseType(userToken, courseTypeName);
      return ResponseEntity.status(HttpStatus.CREATED).body("Course type " + courseTypeName + " created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Deprecated
  @GetMapping(value = {"/courseTypes/get"})
  public ResponseEntity<?> getCourseType(@RequestHeader String userToken, @RequestParam Integer id) {
    try {
      CourseType courseType = service.getCourseType(userToken, id);
      return ResponseEntity.ok().body(new CourseTypeDTO(courseType));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {"/courseTypes/getAll"})
  public ResponseEntity<?> getAllCourseTypes(@RequestHeader String userToken,
                                             @RequestParam(required = false) Boolean approved,
                                             @RequestParam(required = false) Boolean rejected,
                                             @RequestParam(required = false) Integer instructorId){
    try {
      List<CourseType> courseTypes = service.getAllCourseTypes(userToken, approved, rejected, instructorId);
      List<CourseTypeDTO> courseTypeDTOs = courseTypes.stream().map(CourseTypeDTO::new).toList();

      return ResponseEntity.ok().body(courseTypeDTOs);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {"/courseTypes/getAllApproved"})
  public ResponseEntity<?> getAllApprovedCourseTypes() {
    try {
      List<CourseType> courseTypes = service.getAllApprovedCourseTypes();
      List<CourseTypeDTO> courseTypeDTOs = courseTypes.stream().map(CourseTypeDTO::new).toList();

      return ResponseEntity.ok().body(courseTypeDTOs);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {"/courseTypes/getProposed"})
  public ResponseEntity<?> getProposedCourseTypes(@RequestHeader String userToken) {
    try {
      List<CourseType> courseTypes = service.getProposedCourseTypes(userToken);
      List<CourseTypeDTO> courseTypeDTOs = courseTypes.stream().map(CourseTypeDTO::new).toList();

      return ResponseEntity.ok().body(courseTypeDTOs);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {"/courseTypes/updateApproval"})
  public ResponseEntity<?> updateCourseType(@RequestHeader String userToken, @RequestParam Integer id){
    try {
      service.updateCourseTypeApproval(userToken, id);
      return ResponseEntity.ok().body("Course type approval updated successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {"/courseTypes/updateRejection"})
  public ResponseEntity<?> updateCourseTypeRejection(@RequestHeader String userToken, @RequestParam Integer id){
    try {
      service.updateCourseTypeRejection(userToken, id);
      return ResponseEntity.ok().body("Course type rejection updated successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {"/courseTypes/delete"})
  public ResponseEntity<?> deleteCourseType(@RequestHeader String userToken, @RequestParam Integer id) {
    try {
      service.deleteCourseType(userToken, id);
      return ResponseEntity.ok().body("Course type deleted successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
