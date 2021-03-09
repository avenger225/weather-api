package com.project.security.controller;

import com.project.security.configuration.LoginCredentials;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {

    @PostMapping("")
    public void login(@RequestBody LoginCredentials loginCredentials) {}

    @GetMapping("/test")
    public String secured() {
        return "test";
    }
}
