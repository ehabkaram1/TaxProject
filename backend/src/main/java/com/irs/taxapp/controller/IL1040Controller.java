package com.irs.taxapp.controller;

import com.irs.taxapp.model.TaxFilingData;
import com.irs.taxapp.service.IL1040Service;
import com.itextpdf.io.exceptions.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/il1040")
@CrossOrigin(origins = "http://localhost:3000")
public class IL1040Controller {
    
    @Autowired
    private IL1040Service il1040Service;

    @PostMapping("/generate")
    public ResponseEntity<?> generateIL1040(@RequestBody TaxFilingData taxData) {
        try {
            System.out.println("Attempting to generate IL-1040...");
            
            if (taxData == null || taxData.getPersonalInfo() == null || taxData.getW2Data() == null) {
                return ResponseEntity
                    .badRequest()
                    .body("Invalid tax filing data");
            }

            byte[] pdfBytes = il1040Service.generateForm(taxData);
            
            if (pdfBytes == null || pdfBytes.length == 0) {
                throw new IOException("Generated PDF is empty");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                    .filename("IL-1040.pdf")
                    .build()
            );

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            System.err.println("Error generating IL-1040: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error generating IL-1040: " + e.getMessage());
        }
    }
}