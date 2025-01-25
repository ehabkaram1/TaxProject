package com.irs.taxapp.utils;

import com.irs.taxapp.model.W2Data;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class W2Parser {

    public static W2Data parseW2Text(String extractedText) {
        // Improve employer and employee details extraction
        String employerName = extractEmployerName(extractedText);
        String employerAddress = extractPattern(extractedText, "(?m)(?<=\\n).*?(?=\\n\\d{2}-\\d{7})"); // Line after employer name

        String employeeName = extractPattern(extractedText, "(?m)^([A-Z ]{3,})(?=\\n\\d{3,})"); // Looks for uppercase names before numbers
        String employeeAddress = extractPattern(extractedText, "(?m)(?<=\\n[A-Z ]{3,}\\n).*?(?=\\n\\d{2}-\\d{7})"); // Address follows name

        // Extract tax details
        String employerEIN = extractPattern(extractedText, "(\\d{2}-\\d{7})"); // Matches EIN format XX-XXXXXXX
        String employeeSSN = extractPattern(extractedText, "(XXX-XX-\\d{4})"); // Matches last 4 digits of SSN

        double wages = extractAmount(extractedText, "1\\s+Wages, tips, other comp");
        double federalTaxWithheld = extractAmount(extractedText, "2\\s+Federal income tax withheld");
        double stateWages = extractAmount(extractedText, "16\\s+State wages, tips, etc");
        double stateTaxWithheld = extractAmount(extractedText, "17\\s+State income tax");

        // Extract state dynamically (looks for state abbreviation before the employer ID)
        String state = extractPattern(extractedText, "\\b[A-Z]{2}\\b(?=\\s+\\d{2}-\\d{7})");

        return new W2Data(
            employerName, employerAddress, employeeName, employeeAddress,
            employerEIN, employeeSSN, wages, federalTaxWithheld,
            stateTaxWithheld, stateWages, state
        );
    }

    // Extracts a general pattern (used for EIN, SSN, employer details)
    private static String extractPattern(String text, String regex) {
        Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher m = p.matcher(text);
        return m.find() ? m.group().trim() : "Not Found";
    }

    // Extracts numeric values (e.g., wages, tax amounts)
    private static double extractAmount(String text, String label) {
        Pattern p = Pattern.compile(label + "\\s+(\\d{1,7}\\.\\d{2})");
        Matcher m = p.matcher(text);
        return m.find() ? Double.parseDouble(m.group(1)) : 0.0;
    }

    private static String extractEmployerName(String text) {
        Pattern p = Pattern.compile("(?<=\\n)[A-Z ]{3,}(?=\\n\\d)");
        Matcher m = p.matcher(text);
        return m.find() ? m.group().trim() : "Not Found";
    }
}
