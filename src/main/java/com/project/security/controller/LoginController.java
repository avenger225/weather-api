package com.project.security.controller;

import com.project.security.configuration.LoginCredentials;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {

    @ApiOperation(value = "Authorization method - use in order to receive JWT token")
    @PostMapping("")
    public void login(@RequestBody LoginCredentials loginCredentials) {}

    @ApiOperation(value = "Test method")
    @GetMapping("/test")
    public String secured() {
        return "test";
    }
}
