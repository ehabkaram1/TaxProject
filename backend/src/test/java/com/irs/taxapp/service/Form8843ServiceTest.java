package com.irs.taxapp.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

public class Form8843ServiceTest {

    @Test
public void listAllPDFFields() throws IOException {
    // Load the PDF from resources folder
    ClassPathResource resource = new ClassPathResource("forms/f8843.pdf");
    PDDocument document = PDDocument.load(resource.getInputStream());
    try {
        PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
        if (acroForm != null) {
            System.out.println("=== PDF Form Fields ===");
            List<PDField> fields = acroForm.getFields();
            if (fields.isEmpty()) {
                System.out.println("No fillable fields found in the PDF!");
            } else {
                fields.forEach(field -> {
                    System.out.println("Field Name: " + field.getFullyQualifiedName());
                    System.out.println("Field Type: " + field.getClass().getSimpleName());
                    
                    String fieldValue = field.getValueAsString();
                    System.out.println("Current Value: " + fieldValue);
                    
                    System.out.println("-------------------");
                });
                System.out.println("Total fields found: " + fields.size());
            }
        } else {
            System.out.println("No form fields found in the PDF!");
        }
    } finally {
        document.close();
    }
}

    @Test
    public void testFieldWriting() throws IOException {
        // Load the PDF
        ClassPathResource resource = new ClassPathResource("forms/f8843.pdf");
        PDDocument document = PDDocument.load(resource.getInputStream());
        
        try {
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
            if (acroForm != null) {
                // Try to write to a field
                PDField testField = acroForm.getField("topmostSubform[0].Page1[0].f1_1[0]"); // Use a field name from the first test
                if (testField != null) {
                    testField.setValue("Test Value");
                    System.out.println("Successfully wrote to field");
                    System.out.println("New Value: " + testField.getValueAsString());
                } else {
                    System.out.println("Test field not found!");
                }
            }
        } finally {
            document.close();
        }
    }
}