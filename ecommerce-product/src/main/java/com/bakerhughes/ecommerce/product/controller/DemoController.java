package com.bakerhughes.ecommerce.product.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping("/hello")
    @PreAuthorize("hasRole('client-user')")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/hello2")
    @PreAuthorize("hasRole('client-admin')")
    public String hello2() {
        return "Hello World - 2 - Admin";
    }
}
