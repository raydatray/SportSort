package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.RegistrationDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.Registration;
import ca.mcgill.ecse321.sportsregistrationw24.service.RegistrationCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class RegistrationCreationRestController {
    @Autowired
    private RegistrationCreationService service;

    @PostMapping(value = {"/registrations/{courseOfferingId}/{userAccountEmail}/{registrationDate}", "/registrations/{courseOfferingId}/{userAccountEmail}/{registrationDate}/"})
    public RegistrationDto createRegistration(@PathVariable("courseOfferingId") Integer courseOfferingId,
                                              @PathVariable("userAccountEmail") String userAccountEmail,
                                              @PathVariable("registrationDate") Date registrationDate) {
        Registration registration = service.createRegistration(courseOfferingId, userAccountEmail, registrationDate);
        return convertToDto(registration);
    }

    // NOTE: CHANGE FROM ID TO SMTH ELSE MAYBE
    @GetMapping(value = {"/registrations/{id}", "/registrations/{id}/"})
    public Registration getRegistration() {
        return new Registration();
    }

    @GetMapping()
    public List<RegistrationDto> getAllRegistrations() {
        List<RegistrationDto> registrationDtos = new ArrayList<>();
        for (Registration registration : service.getAllRegistrations()) {
            registrationDtos.add(convertToDto(registration));
        }
        return registrationDtos;
    }

    private RegistrationDto convertToDto(Registration registration) {
        if (registration == null) {
            throw new IllegalArgumentException("There is no such registration!");
        }
        return new RegistrationDto(registration.getCourseOffering().getId(), registration.getCustomerAccount().getEmail(), registration.getRegisteredDate());
    }
}
