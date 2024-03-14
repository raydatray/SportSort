package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.RegistrationCO;
import ca.mcgill.ecse321.sportsregistrationw24.dto.RegistrationDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.Registration;
import ca.mcgill.ecse321.sportsregistrationw24.model.keys.RegistrationId;
import ca.mcgill.ecse321.sportsregistrationw24.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
            return ResponseEntity.ok().body(registration);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = {
            "/registrations/get",
            "/registrations/get/"
    })
    public ResponseEntity<?> getRegistration(@RequestParam RegistrationId registrationId) {
        try {
            Registration registration = registrationService.getRegistration(registrationId);
            return ResponseEntity.ok().body(registration);
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
            List<RegistrationDto> registrationCOs = new ArrayList<>();
            for (Registration registration : registrationService.getAllRegistrations()) {
                registrationCOs.add(convertToDto(registration));
            }
            return ResponseEntity.ok().body(registrationCOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private RegistrationDto convertToDto(Registration registration) {
        if (registration == null) {
            throw new IllegalArgumentException("There is no such registration!");
        }

        return new RegistrationDto(registration.getCourseOffering().getCourseType().getCourseName(),
                registration.getId(), Integer.toString(registration.getPaymentInfo().getCardNumber()),
                100, registration.getRegisteredDate());
    }
}