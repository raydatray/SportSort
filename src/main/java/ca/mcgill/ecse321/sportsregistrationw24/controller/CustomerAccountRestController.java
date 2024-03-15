package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.CustomerAccountDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.service.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createCustomerAccount(@RequestBody CustomerAccountDto customerAccountDto) {
        try {
            String email = customerAccountDto.getEmail();
            String password = customerAccountDto.getPassword();
            CustomerAccount customerAccount = service.createCustomerAccount(email, password);
            return ResponseEntity.ok().body(convertToDto(customerAccount));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = {
            "/customerAccounts/get",
            "/customerAccounts/get/"
    })
    public ResponseEntity<?> getCustomerAccount(@RequestParam String email) {
        try {
            CustomerAccount customerAccount = service.getCustomerAccount(email);
            return ResponseEntity.ok().body(convertToDto(customerAccount));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = {
            "/customerAccounts/getAll",
            "/customerAccounts/getAll/"
    })
    public ResponseEntity<?> getAllCustomerAccounts() {
        try {
            List<CustomerAccountDto> customerAccountDtos = new ArrayList<>();
            for (CustomerAccount customerAccount : service.getAllCustomerAccounts()) {
                CustomerAccountDto dto = convertToDto(customerAccount);
                customerAccountDtos.add(dto);
            }
            return ResponseEntity.ok().body(customerAccountDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = {
            "/customerAccounts/updateEmail",
            "/customerAccounts/updateEmail/"
    })

    public ResponseEntity<?> updateCustomerEmail(@RequestBody CustomerAccountDto customerAccountDto, @RequestParam String newEmail) {
        try {
            String oldEmail = customerAccountDto.getEmail();
            service.updateCustomerEmail(oldEmail, newEmail);
            return ResponseEntity.ok().body("Customer account updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = {
            "/customerAccounts/updatePassword",
            "/customerAccounts/updatePassword/"
    })
    public ResponseEntity<?> updateCustomerPassword(@RequestBody CustomerAccountDto customerAccountDto, String newPassword) {
        try {
            CustomerAccount customerAccount = service.getCustomerAccount(customerAccountDto.getEmail());
            String oldPassword = customerAccountDto.getPassword();
            service.updateCustomerPassword(customerAccount, newPassword, oldPassword);
            return ResponseEntity.ok().body("Password updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = {
            "/customerAccounts/delete",
            "/customerAccounts/delete/"
    })
    public ResponseEntity<?> deleteCustomerAccount(@RequestParam String email) {
        try {
            service.deleteCustomerAccount(email);
            return ResponseEntity.ok().body("Customer account deleted successfully.");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private CustomerAccountDto convertToDto(CustomerAccount customerAccount) {
        if (customerAccount == null) {
            throw new IllegalArgumentException("There is no such customer account!");
        }
        return new CustomerAccountDto(customerAccount.getEmail(),
                customerAccount.getPassword());
    }

}
