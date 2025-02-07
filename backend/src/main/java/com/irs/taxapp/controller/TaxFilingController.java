package com.irs.taxapp.controller;

import com.irs.taxapp.model.TaxFilingData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tax-filing")
public class TaxFilingController {

    @PostMapping
    public ResponseEntity<?> saveTaxFilingData(@RequestBody TaxFilingData taxFilingData) {
        try {
            // TODO: Add your logic to save tax filing data to database if needed
            
            // For now, we'll just return the received data
            return ResponseEntity.ok(taxFilingData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Failed to save tax filing data: " + e.getMessage());
        }
    }
}
