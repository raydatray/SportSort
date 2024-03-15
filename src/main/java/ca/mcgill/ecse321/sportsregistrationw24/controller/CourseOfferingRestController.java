package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.CourseOfferingDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.CourseOffering;
import ca.mcgill.ecse321.sportsregistrationw24.model.Room;
import ca.mcgill.ecse321.sportsregistrationw24.service.CourseOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
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
            "/courseOffering/create/"
    })
    public CourseOfferingDto createCourseOffering(@RequestBody CourseOfferingDto courseOfferingDto) {
        Date startDate = courseOfferingDto.getStartDate();
        Date endDate = courseOfferingDto.getEndDate();
        Room room = courseOfferingDto.getRoom();
        Integer id = courseOfferingDto.getiD();


       CourseOffering courseOffering = service.createCourseOffering(startDate, endDate, room, id);
        return convertToDto(courseOffering);
    }

    @GetMapping(value = {
            "/courseOfferings/get",
            "/courseOfferings/get/"
    })
    public CourseOfferingDto getCourseOffering(@RequestParam Integer id) {
       CourseOffering courseOffering = service.getCourseOffering(id);
        return convertToDto(courseOffering);
    }

    @GetMapping(value = {
            "/courseOfferings/getAll",
            "/courseOfferings/getAll/"
    })
    public List<CourseOfferingDto> getAllCourseOfferings() {
        List<CourseOfferingDto> courseOfferingDtos = new ArrayList<>();
        for (CourseOffering courseOffering : service.getAllCourseOfferings()) {
            courseOfferingDtos.add(convertToDto(courseOffering));
        }
        return courseOfferingDtos;
    }

    @PutMapping(value = {
            "/courseOfferings/update",
            "/courseOfferings/update/"
    })

    public void updateCustomerAccount(@RequestBody CourseOfferingDto courseOfferingDto, @RequestParam Date aStartDate, Date aEndDate, Room aRoom, Integer aId) {
        Date startDate = courseOfferingDto.getStartDate();
        Date endDate = courseOfferingDto.getEndDate();
        Room room = courseOfferingDto.getRoom();

        service.updateCourseOffering(startDate,endDate,room, aId);
    }

    @DeleteMapping(value = {
            "/courseOfferings/delete",
            "/courseOfferings/delete/"
    })
    public void deleteCourseOffering(@RequestParam Integer id) {
        service.deleteCourseOffering(id);
    }

    private CourseOfferingDto convertToDto(CourseOffering courseOffering) {
        if (courseOffering == null) {
            throw new IllegalArgumentException("There is no such course offering!");
        }
        return new CourseOfferingDto(courseOffering.getStartDate(),
                courseOffering.getEndDate(), courseOffering.getRoom(), courseOffering.getId());
    }

}
