package com.irs.taxapp.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class W2Extractor {

    public static String extractTextFromPDF(File pdfFile) {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR: Could not extract text from W-2 PDF - " + e.getMessage();
        }
    }
}
