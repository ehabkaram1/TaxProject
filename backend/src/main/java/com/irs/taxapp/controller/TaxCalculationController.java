package com.irs.taxapp.controller;

import com.irs.taxapp.model.W2Data;
import com.irs.taxapp.common.ErrorResponse;
import com.irs.taxapp.model.PersonalInfo;
import com.irs.taxapp.model.TaxFilingData;
import com.irs.taxapp.service.TaxCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tax")
@CrossOrigin(origins = "http://localhost:3000")
public class TaxCalculationController {

    @Autowired
    private TaxCalculationService taxCalculationService;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateTaxes(@RequestBody TaxFilingData taxFilingData) {
        try {
            Map<String, Double> calculationResults = taxCalculationService.calculateTaxes(taxFilingData);
            
            // Structure the response to match frontend expectations
            Map<String, Object> response = new HashMap<>();
            Map<String, Object> calculations = new HashMap<>();
            
            // Move all calculation results under 'calculations' key
            calculationResults.forEach((key, value) -> {
                calculations.put(key, value);
            });
            
            // Create the final structure
            response.put("calculations", calculations);
            if (calculationResults.containsKey("bracketBreakdown")) {
                response.put("bracketBreakdown", calculationResults.get("bracketBreakdown"));
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Tax calculation failed: " + e.getMessage()));
        }
    }
}


class TaxCalculationRequest {
    private W2Data w2Data;
    private PersonalInfo personalInfo;

    // Getters and setters
    public W2Data getW2Data() { return w2Data; }
    public void setW2Data(W2Data w2Data) { this.w2Data = w2Data; }
    public PersonalInfo getPersonalInfo() { return personalInfo; }
    public void setPersonalInfo(PersonalInfo personalInfo) { this.personalInfo = personalInfo; }
}

class TaxCalculationResponse {
    private Map<String, Double> calculations;
    private Map<String, Double> bracketBreakdown;

    public TaxCalculationResponse(Map<String, Double> calculations, Map<String, Double> bracketBreakdown) {
        this.calculations = calculations;
        this.bracketBreakdown = bracketBreakdown;
    }

    // Getters
    public Map<String, Double> getCalculations() { return calculations; }
    public Map<String, Double> getBracketBreakdown() { return bracketBreakdown; }
}