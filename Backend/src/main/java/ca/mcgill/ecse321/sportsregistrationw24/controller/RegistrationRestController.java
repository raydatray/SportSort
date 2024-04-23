package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.Registration.RegistrationCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.Registration.RegistrationCustomerDTO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.Registration.RegistrationStaffDTO;
import ca.mcgill.ecse321.sportsregistrationw24.model.Registration;
import ca.mcgill.ecse321.sportsregistrationw24.model.UserAccount;
import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import ca.mcgill.ecse321.sportsregistrationw24.service.RegistrationService;
import ca.mcgill.ecse321.sportsregistrationw24.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class RegistrationRestController {
  @Autowired
  private RegistrationService service;
  @Autowired
  private UserAccountService userService;

  @PostMapping(value = {"/registrations/create"})
  public ResponseEntity<?> createRegistration(@RequestHeader String userToken, @RequestBody RegistrationCO registrationCO) {
    try {
      service.createRegistration(userToken, registrationCO.getCourseOfferingId(), registrationCO.getPaymentInfoId(), registrationCO.getPricePaid(), registrationCO.getRegistrationDate());
      return ResponseEntity.status(HttpStatus.CREATED).body("Registration created successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  /**
   * public ResponseEntity<?> getRegistration(@RequestParam Integer customerAccountID, @RequestParam Integer courseOfferingID) {
   * try {
   * RegistrationId registrationId = new RegistrationId(customerAccountID, courseOfferingID);
   * Registration registration = service.getRegistration(registrationId);
   * <p>
   * return ResponseEntity.ok().body(convertToDto(registration));
   * } catch (Exception e) {
   * return ResponseEntity.badRequest().body(e.getMessage());
   * }
   * }
   **/

  @GetMapping(value = {"registrations/getByCustomer"})
  public ResponseEntity<?> getRegistrationsByCustomer(@RequestHeader String userToken) {
    try {
      List<Registration> registrations = service.getAllRegistrationsByCustomer(userToken);
      List<RegistrationCustomerDTO> registrationDTOs = registrations.stream().map(RegistrationCustomerDTO::new).toList();

      return ResponseEntity.ok().body(registrationDTOs);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {"registration/getByCourseOffering"})
  public ResponseEntity<?> getRegistrationsByCourseOffering(@RequestHeader String userToken, @RequestParam Integer courseOfferingID) {
    try {
      List<Registration> registrations = service.getAllRegistrationsByCourseOffering(userToken, courseOfferingID);
      List<RegistrationCustomerDTO> registrationDTOs = registrations.stream().map(RegistrationCustomerDTO::new).toList();

      return ResponseEntity.ok().body(registrationDTOs);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {"/registrations/getAll"})
  public ResponseEntity<?> getAllRegistrations(@RequestHeader String userToken,
                                               @RequestParam(required = false) Date l,
                                               @RequestParam(required = false) Date h,
                                               @RequestParam(required = false) List<Integer> id,
                                               @RequestParam(required = false) String email) {
    try {
      List<Registration> registrations = service.getAllRegistrations(userToken, l, h, id, email);
      List<RegistrationStaffDTO> registrationDTOs = registrations.stream().map(RegistrationStaffDTO::new).toList();

      return ResponseEntity.ok().body(registrationDTOs);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {"/registrations/delete"})
  public ResponseEntity<?> deleteRegistration(@RequestHeader String userToken, @RequestHeader Integer courseOfferingId) {
    try {
      UserAccount user = userService.getUserByToken(userToken);
      Integer userId = user.getId();

      RegistrationId registrationId = new RegistrationId(userId, courseOfferingId);
      service.deleteRegistration(registrationId);
      return ResponseEntity.ok().body("Registration has been successfully deleted!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}