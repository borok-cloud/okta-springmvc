package org.mars.demookta.springmvc;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AppController {


    @GetMapping("/api/resource")
    public ResponseEntity<String> getResource() {
        return ResponseEntity.ok("Resource data");
    }
    @GetMapping("/public")
    public String getPublicResource() {
        String response = "This is a public resource.";
        System.out.println("Response: " + response);  // Log the response
        return response;
    }



    @GetMapping("/admin")
    //@PreAuthorize("hasAuthority('Admin)")
    public String adminPage(Model model) {
        model.addAttribute("message", "This is a Admin page, accessible only by authenticated users.");
        return "Admin Page";
    }

    @GetMapping("/secure")
    public String securePage(Model model) {
        model.addAttribute("message", "This is a secure page, accessible only by authenticated users.");
        return "secure";
    }
}

