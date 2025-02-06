package com.irs.taxapp.service;

import com.irs.taxapp.model.TaxFilingData;
import com.irs.taxapp.model.W2Data;
import com.irs.taxapp.model.PersonalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaxCalculationService {

    @Autowired
    private TaxTreatyService taxTreatyService;

    private static final double NRA_STANDARD_DEDUCTION = 7_850.0;
    private static final double IL_TAX_RATE = 0.0495; // 4.95%
    private static final double IL_PERSONAL_EXEMPTION = 2_425.0;

    private static final double[][] TAX_BRACKETS_2023 = {
        {0, 11600, 0.10},
        {11600, 47150, 0.12},
        {47150, 100525, 0.22},
        {100525, 191950, 0.24},
        {191950, 243725, 0.32},
        {243725, 609350, 0.35},
        {609350, Double.MAX_VALUE, 0.37}
    };

    public Map<String, Double> calculateTaxes(TaxFilingData taxFilingData) {
        Map<String, Double> results = new HashMap<>();
        
        W2Data w2Data = taxFilingData.getW2Data();
        PersonalInfo personalInfo = taxFilingData.getPersonalInfo();

        // Debug prints
        System.out.println("Citizenship Country: " + personalInfo.getCitizenshipCountry());
        System.out.println("Visa Type: " + personalInfo.getVisaType());

        // Determine standard deduction based on treaty
        double standardDeduction = NRA_STANDARD_DEDUCTION; // default for non-resident aliens
        boolean hasTreatyBenefit = false;
        double treatyDeduction = 0.0;
        
        // Check specifically for India (IN) country code
        if ("IN".equals(personalInfo.getCitizenshipCountry())) {
            System.out.println("Indian citizen detected - checking treaty benefits");
            treatyDeduction = taxTreatyService.calculateIndiaTreatyBenefit(
                personalInfo.getVisaType()
            );
            
            System.out.println("Treaty deduction calculated: " + treatyDeduction);
            
            if (treatyDeduction > 0) {
                standardDeduction = treatyDeduction;
                hasTreatyBenefit = true;
                results.put("treatyArticle", Double.parseDouble(taxTreatyService.getIndiaTreatyArticle().replace("(", "").replace(")", "")));
                results.put("treatyStandardDeduction", treatyDeduction);
                System.out.println("Treaty benefit applied. Using standard deduction: " + standardDeduction);
            }
        } else {
            System.out.println("Using regular NRA standard deduction: " + NRA_STANDARD_DEDUCTION);
        }

        // Calculate taxable income with appropriate standard deduction
        double taxableIncome = Math.max(0, w2Data.getWages() - standardDeduction);
        System.out.println("Wages: " + w2Data.getWages());
        System.out.println("Standard Deduction Used: " + standardDeduction);
        System.out.println("Calculated Taxable Income: " + taxableIncome);


        // ✅ Add additional income (lotteries, bonuses, returns)
        if (personalInfo.getAdditionalIncome() > 0) {
            taxableIncome += personalInfo.getAdditionalIncome();
            results.put("additionalIncome", personalInfo.getAdditionalIncome());
        } else {
            results.put("additionalIncome", 0.0);
        }

        // ✅ Calculate taxable income with appropriate standard deduction
        taxableIncome = Math.max(0, w2Data.getWages() + taxableIncome - standardDeduction);

        

        // ✅ Federal Tax Calculation
        double calculatedFederalTax = calculateFederalTax(taxableIncome);
        double federalTaxWithheld = w2Data.getFederalTaxWithheld();
        double federalRefundOrOwe = federalTaxWithheld - calculatedFederalTax;

        results.put("standardDeduction", standardDeduction);
        results.put("hasTreatyBenefit", hasTreatyBenefit ? 1.0 : 0.0);
        results.put("taxableIncome", taxableIncome);
        results.put("grossIncome", w2Data.getWages());
        results.put("calculatedFederalTax", calculatedFederalTax);
        results.put("federalTaxWithheld", federalTaxWithheld);
        results.put("federalRefundOrOwe", federalRefundOrOwe);

        // ✅ Illinois State Tax Calculation
        double stateWages = w2Data.getStateWages();
        double stateTaxableIncome = stateWages;

        // ✅ Apply IL Personal Exemption if not claimed as dependent
        if (!personalInfo.isCanBeClaimed()) {
            stateTaxableIncome = Math.max(0, stateWages - IL_PERSONAL_EXEMPTION);
            results.put("ilPersonalExemption", IL_PERSONAL_EXEMPTION);
        } else {
            results.put("ilPersonalExemption", 0.0);
        }

        // ✅ Add additional income to state taxable income
        stateTaxableIncome += personalInfo.getAdditionalIncome();

        // ✅ Calculate State Tax
        double stateTax = calculateStateTax(stateTaxableIncome);
        double stateRefundOrOwe = w2Data.getStateTaxWithheld() - stateTax;

        results.put("calculatedStateTax", stateTax);
        results.put("stateTaxWithheld", w2Data.getStateTaxWithheld());
        results.put("stateRefundOrOwe", stateRefundOrOwe);

        // ✅ Total Refund or Amount Owed
        double totalRefundOrOwe = federalRefundOrOwe + stateRefundOrOwe;
        results.put("totalRefundOrOwe", totalRefundOrOwe);

        if (hasTreatyBenefit) {
            results.put("treatyDeductionIncrease", treatyDeduction - NRA_STANDARD_DEDUCTION);
            results.put("treatyDetails", 21.2); // Article 21(2)
        }

        Map<String, Double> brackets = getTaxBracketBreakdown(taxableIncome);
        for (Map.Entry<String, Double> bracket : brackets.entrySet()) {
            results.put("bracket_" + bracket.getKey(), bracket.getValue());
        }

        return results;
    }

    private double calculateFederalTax(double taxableIncome) {
        double tax = 0;
        double remainingIncome = taxableIncome;

        for (double[] bracket : TAX_BRACKETS_2023) {
            double bracketStart = bracket[0];
            double bracketEnd = bracket[1];
            double rate = bracket[2];

            if (remainingIncome <= 0) break;

            double taxableInThisBracket = Math.min(remainingIncome, bracketEnd - bracketStart);
            tax += taxableInThisBracket * rate;
            remainingIncome -= taxableInThisBracket;
        }

        return tax;
    }

    private double calculateStateTax(double stateTaxableIncome) {
        return stateTaxableIncome * IL_TAX_RATE;
    }

    private Map<String, Double> getTaxBracketBreakdown(double taxableIncome) {
        Map<String, Double> breakdown = new HashMap<>();
        double remainingIncome = taxableIncome;

        for (int i = 0; i < TAX_BRACKETS_2023.length; i++) {
            if (remainingIncome <= 0) break;

            double bracketStart = TAX_BRACKETS_2023[i][0];
            double bracketEnd = TAX_BRACKETS_2023[i][1];
            double rate = TAX_BRACKETS_2023[i][2];

            double taxableInThisBracket = Math.min(remainingIncome, bracketEnd - bracketStart);
            if (taxableInThisBracket > 0) {
                breakdown.put(String.format("%.0f", rate * 100), taxableInThisBracket);
            }

            remainingIncome -= taxableInThisBracket;
        }

        return breakdown;
    }
}
