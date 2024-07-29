package com.example.springsecurityexercise.controller.C;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginC {
    @GetMapping
    public String viewLogin() {
        return "login";
    }
}
