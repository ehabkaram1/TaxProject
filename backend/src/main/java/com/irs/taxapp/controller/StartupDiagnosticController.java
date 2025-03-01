package com.irs.taxapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StartupDiagnosticController {
    private static final Logger logger = LoggerFactory.getLogger(StartupDiagnosticController.class);

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/diagnostics")
    public Map<String, String> getDiagnostics() {
        Map<String, String> diagnostics = new HashMap<>();
        
        try {
            // Hostname
            diagnostics.put("Hostname", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            diagnostics.put("Hostname", "Could not determine hostname");
        }

        // Environment and System Information
        diagnostics.put("Server Port", serverPort);
        diagnostics.put("Java Version", System.getProperty("java.version"));
        diagnostics.put("OS Name", System.getProperty("os.name"));
        diagnostics.put("User Directory", System.getProperty("user.dir"));
        
        // Log the diagnostics
        logger.info("Startup Diagnostics: {}", diagnostics);
        
        return diagnostics;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("time", new Date().toString());
        response.put("port", System.getenv("PORT"));
        return response;
    }
}