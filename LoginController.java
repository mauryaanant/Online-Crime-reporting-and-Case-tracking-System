package com.project.controller;

import com.project.models.User;
import com.project.services.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // 🔹 Show Login Page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    //  Handle Login
    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {

        User user = userService.findByEmail(email);

        //  Invalid login
        if (user == null || !user.getPassword().equals(password)) {
            return "redirect:/login?error";
        } 
//        else {
//        	return "redirect:/home";
//        };

        
        session.setAttribute("user", user);
        
//        return "redirect:/home";

        
        if ("ADMIN".equalsIgnoreCase(user.getRole().toString())) {
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/"; // Redirect to the User Home page .
        }
        
        
    }

    // 🔹 Show Register Page
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // 🔹 Handle Registration
    @PostMapping("/register")
    public String register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String password) {

        userService.registerUser(name, email, phone, password);

        return "redirect:/login?success";
    }

    // 🔹 Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}