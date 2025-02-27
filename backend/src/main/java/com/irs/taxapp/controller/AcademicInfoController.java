package com.irs.taxapp.controller;

import com.irs.taxapp.model.PersonalInfo;
import com.irs.taxapp.common.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/academic")
public class AcademicInfoController {

    @PostMapping("/save")
    public ResponseEntity<?> saveAcademicInfo(@RequestBody PersonalInfo personalInfo) {
        System.out.println("\n=== Received Academic Info from iOS ===");
        System.out.println("Institution Name: " + personalInfo.getAcademicInstitutionName());
        System.out.println("Institution Address: " + personalInfo.getAcademicInstitutionAddress());
        System.out.println("Institution Phone: " + personalInfo.getAcademicInstitutionPhone());
        System.out.println("Director Name: " + personalInfo.getAcademicDirectorName());
        System.out.println("Director Phone: " + personalInfo.getAcademicDirectorPhone());
        System.out.println("==================================\n");

        try {
            validateAcademicInfo(personalInfo);
            return ResponseEntity.ok(personalInfo);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Invalid academic info: " + e.getMessage()));
        }
    }

    private void validateAcademicInfo(PersonalInfo personalInfo) {
        // Basic validation
        if (personalInfo.getAcademicInstitutionName() == null || personalInfo.getAcademicInstitutionName().trim().isEmpty()) {
            throw new IllegalArgumentException("Institution name is required");
        }
        if (personalInfo.getAcademicInstitutionAddress() == null || personalInfo.getAcademicInstitutionAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Institution address is required");
        }
        
        // Add more validation as needed
    }
}