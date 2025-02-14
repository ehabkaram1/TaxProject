package com.irs.taxapp.controller;

import com.irs.taxapp.model.TaxFilingData;
import com.irs.taxapp.service.Form1040NRService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/form1040nr")
@CrossOrigin(origins = "http://localhost:3000")
public class Form1040NRController {
    
    @Autowired
    private Form1040NRService form1040NRService;

    @PostMapping("/generate")
    public ResponseEntity<?> generate1040NR(@RequestBody TaxFilingData taxData) {
        try {
            System.out.println("Attempting to generate Form 1040NR...");
            
            if (taxData == null || taxData.getPersonalInfo() == null || taxData.getW2Data() == null) {
                return ResponseEntity
                    .badRequest()
                    .body("Invalid tax filing data");
            }

            byte[] pdfBytes = form1040NRService.generateForm(taxData);
            
            if (pdfBytes == null || pdfBytes.length == 0) {
                throw new IOException("Generated PDF is empty");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                    .filename("Form1040NR.pdf")
                    .build()
            );

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            System.err.println("Error generating Form 1040NR: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error generating Form 1040NR: " + e.getMessage());
        }
    }
}