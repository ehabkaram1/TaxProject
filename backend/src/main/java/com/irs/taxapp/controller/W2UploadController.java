package com.irs.taxapp.controller;

import com.irs.taxapp.model.W2Data;
import com.irs.taxapp.utils.W2Extractor;
import com.irs.taxapp.utils.W2Parser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/w2")
public class W2UploadController {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/w2-uploads/";

    @PostMapping("/upload")
    public W2Data uploadW2(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("❌ ERROR: No file selected. Please upload a valid W-2 file.");
        }

        try {
            // Ensure the directory exists
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Save the file
            File saveFile = new File(UPLOAD_DIR + file.getOriginalFilename());
            file.transferTo(saveFile);

            // Extract text from W-2 PDF
            String extractedText = W2Extractor.extractTextFromPDF(saveFile);

            // Parse extracted text into structured W2Data
            return W2Parser.parseW2Text(extractedText);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("❌ ERROR: File upload failed - " + e.getMessage());
        }
    }
}
