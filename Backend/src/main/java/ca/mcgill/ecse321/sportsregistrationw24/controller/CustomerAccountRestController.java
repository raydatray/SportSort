package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.CustomerAccountDto;
import ca.mcgill.ecse321.sportsregistrationw24.dto.CustomerAccountSafeDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.CustomerAccount;
import ca.mcgill.ecse321.sportsregistrationw24.service.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Deprecated
@CrossOrigin(origins = "*")
@RestController
public class CustomerAccountRestController {
  @Autowired
  private CustomerAccountService service;

  @PostMapping(value = {"/customerAccounts/create"})
  public ResponseEntity<?> createCustomerAccount(@RequestBody CustomerAccountDto customerAccountDto) {
    try {
      String email = customerAccountDto.getEmail();
      String password = customerAccountDto.getPassword();
      String name = customerAccountDto.getName();
      service.createCustomerAccount(email, password, name);
      return ResponseEntity.ok().body("Customer account created successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping(value = {
    "/customerAccounts/get",
    "/customerAccounts/get/"
  })
  public ResponseEntity<?> getCustomerAccountByEmail(@RequestParam String email) {
    try {
      CustomerAccount customerAccount = service.getCustomerAccountByEmail(email);
      return ResponseEntity.ok().body(convertToSafeDto(customerAccount));
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
      List<CustomerAccountSafeDto> customerAccountDtos = new ArrayList<>();
      for (CustomerAccount customerAccount : service.getAllCustomerAccounts()) {
        CustomerAccountSafeDto dto = convertToSafeDto(customerAccount);
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

  public ResponseEntity<?> updateCustomerEmail(@RequestParam String newEmail, @RequestHeader String token) {
    try {
      service.updateCustomerEmail(newEmail, token);
      return ResponseEntity.ok().body("Email updated successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping(value = {
    "/customerAccounts/updatePassword",
    "/customerAccounts/updatePassword/"
  })
  public ResponseEntity<?> updateCustomerPassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestHeader String token) {
    try {
      service.updateCustomerPassword(newPassword, oldPassword, token);
      return ResponseEntity.ok().body("Password updated successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {
    "/customerAccounts/deleteByEmail",
    "/customerAccounts/deleteByEmail/"
  })
  public ResponseEntity<?> deleteCustomerAccountByEmail(@RequestParam String email) {
    try {
      service.deleteCustomerAccountByEmail(email);
      return ResponseEntity.ok().body("Customer account deleted successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping(value = {
    "/customerAccounts/delete",
    "/customerAccounts/delete/"
  })
  public ResponseEntity<?> deleteCustomerAccountByToken(@RequestHeader String token) {
    try {
      service.deleteCustomerAccountByToken(token);
      return ResponseEntity.ok().body("Customer account deleted successfully!");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  private CustomerAccountSafeDto convertToSafeDto(CustomerAccount customerAccount) {
    if (customerAccount == null) {
      throw new IllegalArgumentException("There is no such customer account!");
    }
    return new CustomerAccountSafeDto(customerAccount.getEmail(), customerAccount.getName());
  }
}
