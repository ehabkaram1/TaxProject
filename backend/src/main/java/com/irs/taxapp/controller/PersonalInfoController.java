package com.irs.taxapp.controller;

import com.irs.taxapp.model.PersonalInfo;
import com.irs.taxapp.common.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personal")
public class PersonalInfoController {

    @PostMapping("/save")
    public ResponseEntity<?> savePersonalInfo(@RequestBody PersonalInfo personalInfo) {
        System.out.println("\n=== Received Personal Info from iOS ===");
        System.out.println("First Name: " + personalInfo.getFirstName());
        System.out.println("Last Name: " + personalInfo.getLastName());
        System.out.println("SSN: " + personalInfo.getSsn());
        System.out.println("Filing Status: " + personalInfo.getFilingStatus());
        // Print other fields you're interested in
        System.out.println("==================================\n");
        
        try {
            validatePersonalInfo(personalInfo);
            return ResponseEntity.ok(personalInfo);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Invalid personal info: " + e.getMessage()));
        }
    }
    
    private void validatePersonalInfo(PersonalInfo personalInfo) {
        // Basic validation
        if (personalInfo.getFirstName() == null || personalInfo.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (personalInfo.getLastName() == null || personalInfo.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        // Add more validation as needed
    }
}