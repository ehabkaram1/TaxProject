package com.irs.taxapp.utils;

import com.irs.taxapp.model.W2Data;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class W2Parser {

    public static W2Data parseW2Text(String extractedText) {
        // Extract employer name dynamically
        String employerName = extractEmployerName(extractedText);
    
        // Extract employer details (includes employer address + employee name + employee address)
        String employerDetails = extractEmployerDetails(extractedText, employerName);
    
        // Intelligent parsing using defined patterns
        String employerAddress = "Not Found";
        String employeeName = "Not Found";
        String employeeAddress = "Not Found";
    
        // Match Employer Address (Ends with 5-digit ZIP)
        Pattern employerPattern = Pattern.compile("(.*?\\d{5})"); // Captures everything until a 5-digit ZIP code
        Matcher employerMatcher = employerPattern.matcher(employerDetails);
    
        if (employerMatcher.find()) {
            employerAddress = employerMatcher.group(1).trim();
            // **Remove employer address from employerDetails before further extraction**
            employerDetails = employerDetails.replace(employerAddress, "").trim();
        }
    
        // Match Employee Address (Starts with a number and goes until the end)
        Pattern employeePattern = Pattern.compile("(\\d+\\s+.*)$"); // Starts with a number, goes till the end
        Matcher employeeMatcher = employeePattern.matcher(employerDetails);
    
        if (employeeMatcher.find()) {
            employeeAddress = employeeMatcher.group(1).trim();
            // **Remove employee address from employerDetails before extracting the name**
            employerDetails = employerDetails.replace(employeeAddress, "").trim();
        }
    
        // Extract Employee Name (Everything between the two detected addresses)
        employeeName = employerDetails.trim().isEmpty() ? "Not Found" : employerDetails.trim();
    
        // Extract tax details
        String employerEIN = extractPattern(extractedText, "(\\d{2}-\\d{7})"); // EIN format XX-XXXXXXX
        String employeeSSN = extractPattern(extractedText, "(XXX-XX-\\d{4})"); // SSN format XXX-XX-XXXX
    
        double wages = extractAmount(extractedText, "1\\s+Wages, tips, other comp");
        double federalTaxWithheld = extractAmount(extractedText, "2\\s+Federal income tax withheld");
        double stateWages = extractAmount(extractedText, "16\\s+State wages, tips, etc");
        double stateTaxWithheld = extractAmount(extractedText, "17\\s+State income tax");
    
        // Extract state dynamically
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

    private static String extractEmployerDetails(String text, String employerName) {
        if (employerName.equals("Not Found")) {
            return "Not Found"; // Avoid regex processing if employer name isn't found
        }
    
        // Look for the employer name and extract address appearing LATER
        Pattern p = Pattern.compile("(?i)" + Pattern.quote(employerName) + "\\s*\\n(.*?\\n.*?\\n.*?)(?=\\n\\d{2}-\\d{7})", Pattern.DOTALL);
        Matcher m = p.matcher(text);
    
        if (m.find()) {
            return m.group(1).trim().replace("\n", ", ");
        }    
        // Alternative approach: Capture address after the label "Employer's name, address, and ZIP code"
        Pattern altPattern = Pattern.compile("(?<=c\\s+Employer's\\s+name,\\s+address,\\s+and\\s+ZIP\\s+code\\n)(.*?\\n.*?\\n)", Pattern.DOTALL);
        Matcher altMatcher = altPattern.matcher(text);
    
        if (altMatcher.find()) {
            return altMatcher.group(1).trim().replace("\n", ", ");
        }
    
        System.out.println("Employer Address NOT FOUND for: " + employerName);
        return "Not Found";
    }
    
    
    
    
    
}
