package com.irs.taxapp.controller;

import com.irs.taxapp.model.PersonalInfo;
import com.irs.taxapp.model.TaxFilingData;
import com.irs.taxapp.model.W2Data;
import com.irs.taxapp.service.Form8843Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/form8843")
@CrossOrigin(origins = "http://localhost:3000") // Adjust to match your frontend URL
public class Form8843Controller {
    
    @Autowired
    private Form8843Service form8843Service;

    @PostMapping("/generate")
public ResponseEntity<?> generateForm8843(@RequestBody TaxFilingData taxData) {
    try {
        System.out.println("Attempting to generate Form 8843...");
        
        // Validate input data
        if (taxData == null || taxData.getPersonalInfo() == null || taxData.getW2Data() == null) {
            System.err.println("Invalid tax filing data received");
            return ResponseEntity
                .badRequest()
                .body("Invalid tax filing data. Personal info and W2 data are required.");
        }

        // Generate PDF
        byte[] pdfBytes = form8843Service.generateForm(taxData);
        
        if (pdfBytes == null || pdfBytes.length == 0) {
            throw new IOException("Generated PDF is empty");
        }

        // Prepare HTTP headers for PDF download
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
            ContentDisposition.builder("attachment")
                .filename("Form8843.pdf")
                .build()
        );

        // Return PDF as downloadable file
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        
    } catch (IOException e) {
        System.err.println("Error generating Form 8843: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error generating Form 8843: " + e.getMessage());
    } catch (Exception e) {
        System.err.println("Unexpected error: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Unexpected error: " + e.getMessage());
    }
}

    // Optional: Add a method to validate form data before generation
    @PostMapping("/validate")
    public ResponseEntity<?> validateFormData(@RequestBody TaxFilingData taxData) {
        try {
            // Perform comprehensive validation
            if (taxData == null) {
                return ResponseEntity.badRequest().body("No tax filing data provided");
            }

            // Check personal info
            if (taxData.getPersonalInfo() == null) {
                return ResponseEntity.badRequest().body("Personal information is missing");
            }

            // Check W2 data
            if (taxData.getW2Data() == null) {
                return ResponseEntity.badRequest().body("W2 data is missing");
            }

            // Add more specific validations
            validatePersonalInfo(taxData.getPersonalInfo());
            validateW2Data(taxData.getW2Data());

            return ResponseEntity.ok("Data is valid for Form 8843 generation");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Helper method for personal info validation
    private void validatePersonalInfo(PersonalInfo personalInfo) {
        if (personalInfo.getFirstName() == null || personalInfo.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }

        if (personalInfo.getLastName() == null || personalInfo.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }

        // Add more validation checks
        if (personalInfo.getVisaType() == null) {
            throw new IllegalArgumentException("Visa type is required");
        }

        // Add more specific validations as needed
    }

    // Helper method for W2 data validation
    private void validateW2Data(W2Data w2Data) {
        if (w2Data.getEmployeeSSN() == null || w2Data.getEmployeeSSN().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee SSN is required");
        }

        // Add more validation checks
        if (w2Data.getEmployerName() == null || w2Data.getEmployerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employer name is required");
        }

        // Add more specific validations as needed
    }
}