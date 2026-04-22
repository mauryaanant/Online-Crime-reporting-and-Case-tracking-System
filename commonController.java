package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.services.commonService;

@Controller
public class commonController {

    @Autowired
    private commonService commonService;

    @GetMapping("/")
    public String loadIndex(Model model) {

        // Fetch data from service
        String welcomeMsg = commonService.getWelcomeMessage();

        // Send to frontend
        model.addAttribute("welcomeMessage", welcomeMsg);

        return "index";
    }
    
    @GetMapping("/aboutUs.html")
    public String about() {
        return "aboutUs.html";
    }

    @GetMapping("/ContactUs.html")
    public String contact() {
        return "ContactUs.html";
    }

    @GetMapping("/Privacy_policy.html")
    public String privacy() {
        return "Privacy_policy.html";
    }
}