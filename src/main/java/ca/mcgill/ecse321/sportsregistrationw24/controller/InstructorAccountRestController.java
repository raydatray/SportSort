package ca.mcgill.ecse321.sportsregistrationw24.controller;

import ca.mcgill.ecse321.sportsregistrationw24.dto.InstructorAccountDto;
import ca.mcgill.ecse321.sportsregistrationw24.model.InstructorAccount;
import ca.mcgill.ecse321.sportsregistrationw24.service.InstructorAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class InstructorAccountRestController {

    @Autowired
    private InstructorAccountService service;

    @PostMapping(value = {
            "/instructorAccounts/create",
            "/instructorAccounts/create/"
    })
    public ResponseEntity<?> createInstructorAccount(@RequestBody InstructorAccountDto instructorAccountDto) {
        try {
            String email = instructorAccountDto.getEmail();
            String password = instructorAccountDto.getPassword();
            InstructorAccount instructorAccount = service.createInstructorAccount(email, password);
            return ResponseEntity.ok().body(instructorAccount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = {
            "/instructorAccounts/get",
            "/instructorAccounts/get/"
    })
    public ResponseEntity<?> getInstructorAccount(@RequestParam String email) {
        try {
            InstructorAccount instructorAccount = service.getInstructorAccount(email);
            return ResponseEntity.ok().body(instructorAccount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = {
            "/instructorAccounts/getAll",
            "/instructorAccounts/getAll/"
    })
    public ResponseEntity<?> getAllInstructorAccounts() {
        try {
            List<InstructorAccountDto> instructorAccountDtos = new ArrayList<>();
            for (InstructorAccount instructorAccount : service.getAllInstructorAccounts()) {
                InstructorAccountDto dto = convertToDto(instructorAccount);
                instructorAccountDtos.add(dto);
            }
            return ResponseEntity.ok().body(instructorAccountDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = {
            "/instructorAccounts/updateEmail",
            "/instructorAccounts/updateEmail/"
    })
    public ResponseEntity<?> updateInstructorEmail(@RequestBody InstructorAccountDto instructorAccountDto, @RequestParam String oldEmail) {
        try {
            String email = instructorAccountDto.getEmail();
            String password = instructorAccountDto.getPassword();

            service.updateInstructorEmail(oldEmail, email, password);
            return ResponseEntity.ok().body("Customer account updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = {
            "/instructorAccounts/updatePassword",
            "/instructorAccounts/updatePassword/"
    })
    public ResponseEntity<?> updateInstructorPassword(@RequestBody InstructorAccountDto instructorAccountDto, String newPassword) {
        try {
            InstructorAccount instructorAccount = service.getInstructorAccount(instructorAccountDto.getEmail());
            String oldPassword = instructorAccountDto.getPassword();
            service.updateInstructorPassword(instructorAccount, newPassword, oldPassword);
            return ResponseEntity.ok().body("Password updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = {
            "/instructorAccounts/delete",
            "/instructorAccounts/delete/"
    })
    public ResponseEntity<?> deleteInstructorAccount(@RequestParam String email) {
        try {
            service.deleteInstructorAccount(email);
            return ResponseEntity.ok().body("Instructor account deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private InstructorAccountDto convertToDto(InstructorAccount instructorAccount) {
        if (instructorAccount == null) {
            throw new IllegalArgumentException("There is no such customer account!");
        }
        return new InstructorAccountDto(instructorAccount.getEmail(),
                instructorAccount.getPassword());
    }

}
