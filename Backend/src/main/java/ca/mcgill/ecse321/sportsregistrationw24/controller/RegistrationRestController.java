package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.RegistrationCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RegistrationDto;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RegistrationIdDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.Registration;
import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import ca.mcgill.ecse321.sportsregistrationw24.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class RegistrationRestController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = {
      "/registrations/create",
      "/registrations/create/"
    })
    public ResponseEntity<?> createRegistration(@RequestBody RegistrationCO registrationCO) {
      try {
        Registration registration = registrationService.createRegistration(
          registrationCO.getCourseOfferingId(),
          registrationCO.getCustomerAccountId(),
          registrationCO.getPaymentInfoId(),
          registrationCO.getRegistrationDate()
        );
      //return ResponseEntity.ok().body("Registration was created successfully!");
        return ResponseEntity.ok().body(convertToDto(registration));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/registrations/get",
    "/registrations/get/"
  })
  public ResponseEntity<?> getRegistration(@RequestParam Integer customerAccountID, @RequestParam Integer courseOfferingID) {
    try {
      RegistrationId registrationId = new RegistrationId(customerAccountID, courseOfferingID);
      Registration registration = registrationService.getRegistration(registrationId);

      return ResponseEntity.ok().body(convertToDto(registration));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/registrations/getAll",
    "/registrations/getAll/"
  })
  public ResponseEntity<?> getAllRegistrations() {
    try {
      List<RegistrationDto> registrationDtos = new ArrayList<>();

      for (Registration registration : registrationService.getAllRegistrations()) {
        registrationDtos.add(convertToDto(registration));
      }

      return ResponseEntity.ok().body(registrationDtos);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {
    "/registrations/delete",
    "/registrations/delete/"
  })
  public ResponseEntity<?> deleteRegistration(@RequestParam Integer customerAccountID, @RequestParam Integer courseOfferingID) {
    try {
      RegistrationId registrationId = new RegistrationId(customerAccountID, courseOfferingID);
      registrationService.deleteRegistration(registrationId);
      return ResponseEntity.ok().body("Registration has been successfully deleted!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  private RegistrationDto convertToDto(Registration registration) {
    if (registration == null) {
      throw new IllegalArgumentException("There is no such registration!");
    }
    // TODO - Once we figure out where price is going to be stored in the model, change the hardcoded value of 100 to the price variable
    return new RegistrationDto(registration);
  }
}