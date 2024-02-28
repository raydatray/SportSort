package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.CustomerAccountDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.service.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CustomerAccountRestController {

    @Autowired
    private CustomerAccountService service;

    @PostMapping(value = {"/customerAccounts/{email}/{password}/{passwordConfirmation}",
            "/customerAccounts/{email}/{password}/{passwordConfirmation}/"})
    public CustomerAccountDto createCustomerAccount(@PathVariable("email") String email,
                                                   @PathVariable("password") String password,
                                                   @PathVariable("passwordConfirmation") String passwordConfirmation) throws IllegalArgumentException{
        CustomerAccount customerAccount = service.createCustomerAccount(email, password, passwordConfirmation);
        return convertToDto(customerAccount);
    }

    @GetMapping(value = {"/customerAccounts/{email}", "/customerAccounts/{email}/"})
    public CustomerAccountDto getCustomerAccount(@PathVariable("email") String email) {
        CustomerAccount customerAccount = service.getCustomerAccount(email);
        return convertToDto(customerAccount);
    }

    @GetMapping(value = {"/customerAccounts", "/customerAccounts/"})
    public List<CustomerAccountDto> getAllCustomerAccounts() {
        List<CustomerAccountDto> customerAccountDtos = new ArrayList<>();
        for (CustomerAccount customerAccount : service.getAllCustomerAccounts()) {
            customerAccountDtos.add(convertToDto(customerAccount));
        }
        return customerAccountDtos;
    }

    private CustomerAccountDto convertToDto(CustomerAccount customerAccount) {
        if (customerAccount == null) {
            throw new IllegalArgumentException("There is no such customer account!");
        }
        return new CustomerAccountDto(customerAccount.getEmail(),
                customerAccount.getPassword());
    }

}
