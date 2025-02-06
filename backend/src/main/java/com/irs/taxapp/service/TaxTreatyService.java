package com.irs.taxapp.service;

import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Calendar;

@Service
public class TaxTreatyService {
    
    // Constants for India Tax Treaty
    private static final double INDIA_STANDARD_DEDUCTION_2023 = 13850.0;  // Full standard deduction for 2023
    private static final String INDIA_ARTICLE = "21(2)";  // Correct article number
    private static final String[] ELIGIBLE_VISAS = {"F1", "J1", "M1", "Q1"}; // Eligible visa types
    
    public double calculateIndiaTreatyBenefit(String visaType) {
        // Check if visa type is eligible
        if (!isEligibleVisa(visaType)) {
            return 0.0;
        }

        // For eligible Indian students, return the difference between standard deduction
        // and non-resident alien standard deduction
        return INDIA_STANDARD_DEDUCTION_2023;
    }

    private boolean isEligibleVisa(String visaType) {
        if (visaType == null) return false;
        String normalizedVisa = visaType.toUpperCase().replace("-", "");
        for (String eligibleVisa : ELIGIBLE_VISAS) {
            if (normalizedVisa.equals(eligibleVisa)) {
                return true;
            }
        }
        return false;
    }

    public String getIndiaTreatyArticle() {
        return INDIA_ARTICLE;
    }

    public double getIndiaStandardDeduction() {
        return INDIA_STANDARD_DEDUCTION_2023;
    }

    // Helper method to get treaty information
    public IndiaTreatyInfo getIndiaTreatyInfo() {
        return new IndiaTreatyInfo(
            INDIA_STANDARD_DEDUCTION_2023,
            INDIA_ARTICLE
        );
    }
}

class IndiaTreatyInfo {
    private final double standardDeduction;
    private final String articleNumber;

    public IndiaTreatyInfo(double standardDeduction, String articleNumber) {
        this.standardDeduction = standardDeduction;
        this.articleNumber = articleNumber;
    }

    public double getStandardDeduction() { return standardDeduction; }
    public String getArticleNumber() { return articleNumber; }
}