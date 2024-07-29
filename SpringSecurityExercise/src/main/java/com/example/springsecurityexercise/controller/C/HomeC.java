package com.example.springsecurityexercise.controller.C;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class HomeC {
    @GetMapping
    public String viewHome() {
        return "home";
    }
}
