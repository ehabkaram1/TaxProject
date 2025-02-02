package com.irs.taxapp.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class W2Extractor {
    private static final Logger logger = LoggerFactory.getLogger(W2Extractor.class);

    public static String extractTextFromPDF(File pdfFile) {
        logger.info("Starting PDF text extraction for file: {}", pdfFile.getName());
        
        try (PDDocument document = PDDocument.load(pdfFile)) {
            logger.info("PDF loaded successfully. Number of pages: {}", document.getNumberOfPages());
            
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String extractedText = pdfStripper.getText(document);
            
            logger.info("Text extracted successfully. Length: {} characters", extractedText.length());
            logger.debug("Extracted text: {}", extractedText);
            
            return extractedText;
            
        } catch (IOException e) {
            logger.error("Failed to extract text from PDF: {}", e.getMessage(), e);
            throw new RuntimeException("Could not extract text from W-2 PDF: " + e.getMessage(), e);
        }
    }
}