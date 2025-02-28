package com.irs.taxapp.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow cross-origin requests - update with your iOS app's domain in production
        registry.addMapping("/api/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedOrigins("*");  // In production, restrict this to your app's domain
    }
}
