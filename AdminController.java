package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.models.Complaint;
import com.project.models.User;
import com.project.repo.ComplaintRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ComplaintRepository complaintRepository;

    // ✅ Common method for admin check (REUSABLE 🔥)
    private boolean isAdmin(User user) {
        return user != null && "ADMIN".equalsIgnoreCase(user.getRole().toString());
    }

    // 🔹 View all complaints
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        // ✅ Proper admin check
        if (!isAdmin(user)) {
            return "redirect:/login";
        }

        model.addAttribute("complaints", complaintRepository.findAll());
        return "admin-dashboard";
    }

    // 🔹 Update status
    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam Long id,
                               @RequestParam String status,
                               HttpSession session) {

        User user = (User) session.getAttribute("user");

        // ✅ Secure endpoint
        if (!isAdmin(user)) {
            return "redirect:/login";
        }

        Complaint complaint = complaintRepository.findById(id).orElse(null);

        if (complaint != null) {
            complaint.setStatus(status);
            complaintRepository.save(complaint);
        }

        return "redirect:/admin/dashboard";
    }

    // 🔹 Update priority
    @PostMapping("/updatePriority")
    public String updatePriority(@RequestParam Long id,
                                @RequestParam String priority,
                                HttpSession session) {

        User user = (User) session.getAttribute("user");

        // ✅ Secure endpoint
        if (!isAdmin(user)) {
            return "redirect:/login";
        }

        Complaint complaint = complaintRepository.findById(id).orElse(null);

        if (complaint != null) {
            complaint.setPriority(priority);
            complaintRepository.save(complaint);
        }

        return "redirect:/admin/dashboard";
    }
    
    @GetMapping("/profile")
    public String adminProfile(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        if (user == null || !"ADMIN".equalsIgnoreCase(user.getRole().toString())) {
            return "redirect:/login";
        }

        model.addAttribute("admin", user);

        return "admin-profile";
    }
}