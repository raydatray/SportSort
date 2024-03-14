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

    @PostMapping(value = {
            "/customerAccounts/create",
            "/customerAccounts/create/"
    })
    public CustomerAccountDto createCustomerAccount(@RequestBody CustomerAccountDto customerAccountDto) {
        String email = customerAccountDto.getEmail();
        String password = customerAccountDto.getPassword();


        CustomerAccount customerAccount = service.createCustomerAccount(email, password);
        return convertToDto(customerAccount);
    }

    @GetMapping(value = {
            "/customerAccounts/get",
            "/customerAccounts/get/"
    })
    public CustomerAccountDto getCustomerAccount(@RequestParam String email) {
        CustomerAccount customerAccount = service.getCustomerAccount(email);
        return convertToDto(customerAccount);
    }

    @GetMapping(value = {
            "/customerAccounts/getAll",
            "/customerAccounts/getAll/"
    })
    public List<CustomerAccountDto> getAllCustomerAccounts() {
        List<CustomerAccountDto> customerAccountDtos = new ArrayList<>();
        for (CustomerAccount customerAccount : service.getAllCustomerAccounts()) {
            customerAccountDtos.add(convertToDto(customerAccount));
        }
        return customerAccountDtos;
    }

    @PutMapping(value = {
            "/customerAccounts/update",
            "/customerAccounts/update/"
    })

    public void updateCustomerAccount(@RequestBody CustomerAccountDto customerAccountDto, @RequestParam String oldEmail) {
        String email = customerAccountDto.getEmail();
        String password = customerAccountDto.getPassword();

        service.updateCustomerAccount(oldEmail, email, password);
    }

    @DeleteMapping(value = {
            "/customerAccounts/delete",
            "/customerAccounts/delete/"
    })
    public void deleteCustomerAccount(@RequestParam String email) {
        service.deleteCustomerAccount(email);
    }

    private CustomerAccountDto convertToDto(CustomerAccount customerAccount) {
        if (customerAccount == null) {
            throw new IllegalArgumentException("There is no such customer account!");
        }
        return new CustomerAccountDto(customerAccount.getEmail(),
                customerAccount.getPassword());
    }

}
