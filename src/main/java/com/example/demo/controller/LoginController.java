package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author RafaelBizi
 * @project cloud-storage-project-1
 */

@Controller
@RequestMapping("/login")

public class LoginController {
    @GetMapping
    public String loginView(){
        return "login";
    }
}
