package com.irs.taxapp.controller;

import com.irs.taxapp.model.PersonalInfo;
import com.irs.taxapp.common.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/immigration")
public class ImmigrationController {

    @PostMapping("/save")
    public ResponseEntity<?> saveImmigrationInfo(@RequestBody PersonalInfo personalInfo) {
        System.out.println("\n=== Received Immigration Info from iOS ===");
        System.out.println("Visa Type: " + personalInfo.getVisaType());
        System.out.println("Citizenship Country: " + personalInfo.getCitizenshipCountry());
        System.out.println("Passport Country: " + personalInfo.getPassportCountry());
        System.out.println("Passport Number: " + personalInfo.getPassportNumber());
        System.out.println("Arrival Date: " + personalInfo.getArrivalDate());
        System.out.println("Days in US 2023: " + personalInfo.getDaysInUS2023());
        System.out.println("Days in US 2022: " + personalInfo.getDaysInUS2022());
        System.out.println("Days in US 2021: " + personalInfo.getDaysInUS2021());
        //System.out.println("Prior US Income: " + personalInfo.isHadUSIncomePriorYears());
        System.out.println("==================================\n");

        try {
            validateImmigrationInfo(personalInfo);
            return ResponseEntity.ok(personalInfo);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                   .body(new ErrorResponse("Invalid immigration info: " + e.getMessage()));
        }
    }

    private void validateImmigrationInfo(PersonalInfo personalInfo) {
        // Basic validation
        if (personalInfo.getVisaType() == null || personalInfo.getVisaType().trim().isEmpty()) {
            throw new IllegalArgumentException("Visa type is required");
        }
        if (personalInfo.getCitizenshipCountry() == null || personalInfo.getCitizenshipCountry().trim().isEmpty()) {
            throw new IllegalArgumentException("Citizenship country is required");
        }
        if (personalInfo.getArrivalDate() == null) {
            throw new IllegalArgumentException("Arrival date is required");
        }
        
        // Validate day counts
        if (personalInfo.getDaysInUS2023() < 0 || personalInfo.getDaysInUS2023() > 365) {
            throw new IllegalArgumentException("Days in US for 2023 must be between 0 and 365");
        }
        if (personalInfo.getDaysInUS2022() < 0 || personalInfo.getDaysInUS2022() > 365) {
            throw new IllegalArgumentException("Days in US for 2022 must be between 0 and 365");
        }
        if (personalInfo.getDaysInUS2021() < 0 || personalInfo.getDaysInUS2021() > 366) {
            throw new IllegalArgumentException("Days in US for 2021 must be between 0 and 366");
        }
    }
}