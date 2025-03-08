package com.irs.taxapp.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class W2Extractor {
    private static final Logger logger = LoggerFactory.getLogger(W2Extractor.class);

    public static String extractTextFromPDF(File pdfFile) {
        logger.info("=== PDF EXTRACTION DIAGNOSTICS ===");
        logger.info("Java Version: {}", System.getProperty("java.version"));
        logger.info("OS Name: {}", System.getProperty("os.name"));        
        logger.info("Starting PDF text extraction for file: {}", pdfFile.getName());

        try (PDDocument document = PDDocument.load(pdfFile)) {
            logger.info("PDF loaded successfully. Number of pages: {}", document.getNumberOfPages());
            
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String extractedText = pdfStripper.getText(document);
            
            logger.info("Text extracted successfully. Length: {} characters", extractedText.length());
        logger.debug("First 500 characters of extracted text: {}", 
            extractedText.substring(0, Math.min(500, extractedText.length())));
            
            return extractedText;
            
        } catch (IOException e) {
            logger.error("Failed to extract text from PDF: {}", e.getMessage(), e);
            throw new RuntimeException("Could not extract text from W-2 PDF: " + e.getMessage(), e);
        }
    }
}


// public class W2Extractor {
//     private static final Logger logger = LoggerFactory.getLogger(W2Extractor.class);

//     public static String extractTextFromPDF(File pdfFile) {
//         logger.info("=== PDF EXTRACTION DIAGNOSTICS ===");
        
//         // Environment Information
//         logger.info("Java Version: {}", System.getProperty("java.version"));
//         logger.info("OS Name: {}", System.getProperty("os.name"));
        
//         // PDFBox Version
//         try {
//             String pdfBoxVersion = org.apache.pdfbox.pdmodel.PDDocument.class.getPackage().getImplementationVersion();
//             logger.info("PDFBox Version: {}", pdfBoxVersion);
//         } catch (Exception e) {
//             logger.warn("Could not determine PDFBox version", e);
//         }

//         logger.info("Starting PDF text extraction for file: {}", pdfFile.getName());
        
//         try (PDDocument document = PDDocument.load(pdfFile)) {
//             // Detailed document information
//             logger.info("PDF loaded successfully.");
//             logger.info("Total Pages: {}", document.getNumberOfPages());
//             logger.info("Is Encrypted: {}", document.isEncrypted());
            
//             // Configure text stripper
//             PDFTextStripper pdfStripper = new PDFTextStripper();
//             pdfStripper.setSortByPosition(true);
            
//             // Extract text with page-by-page analysis
//             StringBuilder fullText = new StringBuilder();
//             for (int page = 1; page <= document.getNumberOfPages(); page++) {
//                 pdfStripper.setStartPage(page);
//                 pdfStripper.setEndPage(page);
//                 String pageText = pdfStripper.getText(document);
                
//                 logger.info("Page {} Text Length: {}", page, pageText.length());
//                 logger.debug("Page {} Preview (first 500 chars):\n{}", 
//                     page, 
//                     pageText.substring(0, Math.min(500, pageText.length()))
//                 );
                
//                 fullText.append(pageText);
//             }
            
//             String extractedText = fullText.toString();
//             logger.info("Total Extracted Text Length: {} characters", extractedText.length());
            
//             // Additional text analysis
//             analyzeExtractedText(extractedText);
            
//             return extractedText;
//         } catch (IOException e) {
//             logger.error("Failed to extract text from PDF: {}", e.getMessage(), e);
//             throw new RuntimeException("Could not extract text from W-2 PDF: " + e.getMessage(), e);
//         }
//     }

//     private static void analyzeExtractedText(String extractedText) {
//         // Analyze text content
//         logger.info("=== TEXT CONTENT ANALYSIS ===");
        
//         // Count occurrences of key W-2 form terms
//         String[] keywords = {
//             "Employer", "Employee", "Wages", "Federal Tax", 
//             "State Tax", "Social Security", "Medicare"
//         };
        
//         for (String keyword : keywords) {
//             int count = extractedText.split(keyword).length - 1;
//             logger.info("Keyword '{}' appears {} times", keyword, count);
//         }
        
//         // Check for specific patterns
//         Pattern[] patterns = {
//             Pattern.compile("\\d{2}-\\d{7}"),  // EIN pattern
//             Pattern.compile("XXX-XX-\\d{4}"),  // SSN pattern
//             Pattern.compile("[A-Z ]+\\s+UNIVERSITY"),  // Employer name pattern
//             Pattern.compile("\\d+\\s+[A-Z ]+,\\s+[A-Z]{2}\\s+\\d{5}")  // Address pattern
//         };
        
//         for (Pattern pattern : patterns) {
//             Matcher matcher = pattern.matcher(extractedText);
//             if (matcher.find()) {
//                 logger.info("Found matching pattern: {}", pattern);
//                 logger.info("First match: {}", matcher.group());
//             }
//         }
//     }
// }