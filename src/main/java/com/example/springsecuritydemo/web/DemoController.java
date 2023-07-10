package com.example.springsecuritydemo.web;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public String getResource() {
        return "This is a test controller";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getaDMINResource() {
        return "This is a test controller (ADMIN)";
    }
}
