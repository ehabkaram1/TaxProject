package com.irs.taxapp.utils;

import com.irs.taxapp.model.W2Data;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





public class W2Parser {

    public static W2Data parseW2Text(String extractedText) {
        System.out.println("\n=== Starting W2 Parsing ===");
        
        // Extract employer/employee information using previous working code
        String employerName = extractEmployerName(extractedText);
        String employerDetails = extractEmployerDetails(extractedText, employerName);
        
        // Parse employer and employee details
        String employerAddress = "";
        String employeeName = "";
        String employeeAddress = "";

        // Match Employer Address (Ends with 5-digit ZIP)
        Pattern employerPattern = Pattern.compile("(.*?\\d{5})");
        Matcher employerMatcher = employerPattern.matcher(employerDetails);

        if (employerMatcher.find()) {
            employerAddress = employerMatcher.group(1).trim();
            employerDetails = employerDetails.replace(employerAddress, "").trim();
        }

        // Match Employee Address (Starts with a number and goes until the end)
        Pattern employeePattern = Pattern.compile("(\\d+\\s+.*)$");
        Matcher employeeMatcher = employeePattern.matcher(employerDetails);

        if (employeeMatcher.find()) {
            employeeAddress = employeeMatcher.group(1).trim();
            employerDetails = employerDetails.replace(employeeAddress, "").trim();
        }

        // Extract Employee Name
        employeeName = employerDetails.trim().isEmpty() ? "Not Found" : employerDetails.trim();
        employeeName = cleanName(employeeName); // Cleaning the name

        // Extract identification numbers
        String employerEIN = extractPattern(extractedText, "(\\d{2}-\\d{7})");
        String employeeSSN = extractPattern(extractedText, "(XXX-XX-\\d{4})");

        // Extract numerical values using improved patterns
        double wages = extractWages(extractedText);
        double federalTaxWithheld = extractFederalTax(extractedText);
        double stateWages = wages; // Using wages as default for state wages
        double stateTaxWithheld = extractStateTax(extractedText);
        String state = extractState(extractedText);

        debugPrintResults(wages, federalTaxWithheld, stateWages, stateTaxWithheld, state);

        return new W2Data(
            employerName, employerAddress, employeeName, employeeAddress,
            employerEIN, employeeSSN, wages, federalTaxWithheld,
            stateTaxWithheld, stateWages, state
        );
    }

    private static double extractWages(String text) {
        debugPrint(text, "Wages (Box 1)");
        Pattern[] patterns = {
            Pattern.compile("GROSS\\s+PAY\\s+(\\d{1,3}(?:,\\d{3})*\\.\\d{2})"),
            Pattern.compile("(?m)^\\s*1\\s+Wages,[^\\n]*?(\\d{1,3}(?:,\\d{3})*\\.\\d{2})"),
            Pattern.compile("(?m)^(\\d{1,3}(?:,\\d{3})*\\.\\d{2})\\s+\\d+\\.\\d{2}\\s*$")
        };
        return findFirstMatch(patterns, text, "Wages");
    }

    private static double extractFederalTax(String text) {
        debugPrint(text, "Federal Tax Withheld (Box 2)");
        Pattern[] patterns = {
            Pattern.compile("FED\\.?\\s*INCOME\\s+(?:TAX\\s+)?WITHHELD\\s+(\\d+\\.\\d{2})"),
            Pattern.compile("BOX\\s+0?2\\s+OF\\s+W-2\\s*(\\d+\\.\\d{2})"),
            Pattern.compile("(?m)^\\s*2\\s+Federal[^\\n]*?(\\d+\\.\\d{2})"),
            Pattern.compile("(?m)^\\d+\\.\\d{2}\\s+(\\d+\\.\\d{2})\\s*$")
        };
        return findFirstMatch(patterns, text, "Federal Tax");
    }

    private static double extractStateTax(String text) {
        debugPrint(text, "State Tax Withheld (Box 17)");
        Pattern[] patterns = {
            Pattern.compile("STATE\\s+INCOME\\s+TAX\\s+(\\d{1,3}(?:,\\d{3})*\\.\\d{2})"),
            Pattern.compile("(?m)^\\s*17\\s+State\\s+income\\s+tax\\s+(\\d{1,3}(?:,\\d{3})*\\.\\d{2})"),
            Pattern.compile("\\d{1,3}(?:,\\d{3})*\\.\\d{2}\\s+(\\d{1,3}(?:,\\d{3})*\\.\\d{2})\\s*$")
        };
        return findFirstMatch(patterns, text, "State Tax");
    }

    private static String extractState(String text) {
        debugPrint(text, "State Code");
        Pattern[] patterns = {
            Pattern.compile("\\b(IL)\\s+36-2167048"),
            Pattern.compile("State\\s+(IL)\\b"),
            Pattern.compile("\\b(IL)\\s+(?:State Tax)"),
            Pattern.compile("\\b[A-Z]{2}\\b(?=\\s+\\d{2}-\\d{7})")  // From previous code
        };
        
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String state = matcher.group(1);
                if (state != null) {
                    System.out.println("Found state: " + state);
                    return state;
                }
            }
        }
        System.out.println("State not found");
        return "Not Found";
    }

    // Your previous working methods for employer/employee extraction
    private static String extractEmployerName(String text) {
        text = text.replaceAll("[�\\r\\n]+", " ")  // Replace special chars and line breaks with space
        .replaceAll("\\s+", " ")         // Normalize whitespaces
        .trim();

System.out.println("=== Normalized Text Diagnostics ===");
System.out.println("Normalized text length: " + text.length());
System.out.println("Normalized text preview: " + text.substring(0, Math.min(500, text.length())));
    
    // Print first few lines with special character visualization
    
        
        Pattern p = Pattern.compile("(?<=\\n)[A-Z ]{3,}(?=\\n\\d)");
        Matcher m = p.matcher(text);
        
        if (m.find()) {
            String match = m.group().trim();
            System.out.println("Match found: " + match);
            System.out.println("Match start index: " + m.start());
            System.out.println("Match end index: " + m.end());
            return match;
        } else {
            System.out.println("No match found for employer name");
            return "Not Found";
        }
    }
    

    private static String extractEmployerDetails(String text, String employerName) {
        System.out.println("=== Employer Details Extraction Diagnostics ===");
        System.out.println("Employer Name: " + employerName);
        
        if (employerName.equals("Not Found")) {
            return "Not Found";
        }
    
        System.out.println("First pattern attempt:");
        Pattern p = Pattern.compile("(?i)" + Pattern.quote(employerName) + "\\s*\\n(.*?\\n.*?\\n.*?)(?=\\n\\d{2}-\\d{7})", Pattern.DOTALL);
        Matcher m = p.matcher(text);
    
        if (m.find()) {
            String details = m.group(1).trim().replace("\n", ", ");
            System.out.println("First pattern match: " + details);
            return details;
        }
    
        System.out.println("Alternate pattern attempt:");
        Pattern altPattern = Pattern.compile("(?<=c\\s+Employer's\\s+name,\\s+address,\\s+and\\s+ZIP\\s+code\\n)(.*?\\n.*?\\n)", Pattern.DOTALL);
        Matcher altMatcher = altPattern.matcher(text);
    
        if (altMatcher.find()) {
            String details = altMatcher.group(1).trim().replace("\n", ", ");
            System.out.println("Alternate pattern match: " + details);
            return details;
        }
    
        System.out.println("Employer Address NOT FOUND for: " + employerName);
        return "Not Found";
    }

    // Helper methods from new code
    private static String extractPattern(String text, String regex) {
        Pattern p = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher m = p.matcher(text);
        return m.find() ? m.group().trim() : "Not Found";
    }

    private static double findFirstMatch(Pattern[] patterns, String text, String field) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                try {
                    String amount = matcher.group(1);
                    if (amount != null) {
                        amount = amount.replace(",", "").trim();
                        double value = Double.parseDouble(amount);
                        System.out.println("Found " + field + ": " + value);
                        if (value > 0) {
                            return value;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Failed to parse amount for " + field);
                }
            }
        }
        System.out.println("No valid value found for " + field);
        return 0.0;
    }

    private static void debugPrint(String text, String field) {
        System.out.println("\n=== Searching for " + field + " ===");
        int previewLength = Math.min(500, text.length());
        System.out.println("Text preview (first 500 chars): " + text.substring(0, previewLength));
    }


    private static String cleanName(String name) {
        if (name == null) return "Not Found";
        // Replace all commas and trim extra spaces
        return name.replaceAll(",", "").trim().replaceAll("\\s+", " ");
    }
    
    private static void debugPrintResults(double wages, double federalTax, double stateWages, 
                                        double stateTax, String state) {
        System.out.println("\n=== Extracted Values ===");
        System.out.println("Wages: " + wages);
        System.out.println("Federal Tax: " + federalTax);
        System.out.println("State Wages: " + stateWages);
        System.out.println("State Tax: " + stateTax);
        System.out.println("State: " + state);
        System.out.println("=====================\n");
    }




}

