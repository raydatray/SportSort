package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.RegistrationDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.Registration;
import ca.mcgill.ecse321.sportsregistrationw24.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class RegistrationRestController {
    @Autowired
    private RegistrationService service;

    @PostMapping(value = {
            "/registrations/create",
            "/registrations/create/"
    })
    public RegistrationDto createRegistration(@RequestBody RegistrationDto registrationDto) {
        String courseTypeName = registrationDto.getCourseTypeName();
        String instructorEmail = registrationDto.getInstructorEmail();
        Date courseStartDate = registrationDto.getCourseStartDate();
        Date courseEndDate = registrationDto.getCourseEndDate();
        String userAccountEmail = registrationDto.getUserAccountEmail();
        Date registrationDate = registrationDto.getRegistrationDate();

        Registration registration = service.createRegistration(courseTypeName, instructorEmail, courseStartDate,
                courseEndDate, userAccountEmail, registrationDate);

        return convertToDto(registration);
    }

    @GetMapping(value = {
            "/registrations/get",
            "/registrations/get/"
    })
    public RegistrationDto getRegistration(@RequestParam String courseTypeName,
                                           String instructorEmail, Date courseStartDate,
                                           Date courseEndDate, String userAccountEmail,
                                           Date registrationDate) {
        Registration registration = service.getRegistration(courseTypeName, instructorEmail, courseStartDate,
                courseEndDate, userAccountEmail, registrationDate);
        return convertToDto(registration);
    }

    @GetMapping(value = {
            "/registrations/getAll",
            "/registrations/getAll/"
    })
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
        return new RegistrationDto(
                registration.getCourseOffering().getCourseType().getCourseName(),
                registration.getCourseOffering().getInstructorAccount().getEmail(),
                registration.getCourseOffering().getStartDate(),
                registration.getCourseOffering().getEndDate(),
                registration.getCustomerAccount().getEmail(),
                registration.getRegisteredDate()
        );
    }
}
