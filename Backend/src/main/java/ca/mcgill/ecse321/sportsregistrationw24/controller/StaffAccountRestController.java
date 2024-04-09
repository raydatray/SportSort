package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.service.StaffAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@CrossOrigin(origins = "*")
@RestController
public class StaffAccountRestController {
    @Autowired
    private StaffAccountService service;
}
