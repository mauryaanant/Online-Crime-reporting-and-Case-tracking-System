package com.project.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.project.models.Complaint;
import com.project.models.User;
import com.project.models.Department;
import com.project.services.ComplaintService;
import com.project.repo.DepartmentRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private DepartmentRepository departmentRepository; // 🔥 NEW

    // 🔹 Show Complaint Form
    @GetMapping("/complaint")
    public String showForm(HttpSession session, Model model) {

        // 🔒 Session check
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        // 🔥 Load departments
        List<Department> departments = departmentRepository.findAll();

        model.addAttribute("complaint", new Complaint());
        model.addAttribute("departments", departments); // 🔥 IMPORTANT

        return "complaint-form";
    }

    // 🔹 Handle Form Submit
    @PostMapping("/submitComplaint")
    public String submitComplaint(
            @ModelAttribute Complaint complaint,
            @RequestParam("file") MultipartFile file,
            @RequestParam("departmentId") Long departmentId,
            HttpSession session,
            Model model) {

        try {
            User user = (User) session.getAttribute("user");

            if (user == null) {
                return "redirect:/login";
            }

            //  Check if file is empty
            if (file.isEmpty()) {
                model.addAttribute("error", "Please upload an evidence file!");
                return "complaint-form";
            }

            // File size validation (5MB)
            if (file.getSize() > 5 * 1024 * 1024) {
                model.addAttribute("error", "File size must be less than 5MB!");
                return "complaint-form";
            }

             // File type validation
            String contentType = file.getContentType();

            if (!(contentType.equals("image/jpeg") ||
                  contentType.equals("image/png") ||
                  contentType.equals("application/pdf"))) {

                model.addAttribute("error", "Only JPG, PNG, PDF files are allowed!");
                return "complaint-form";
            }

            Complaint saved = complaintService.registerComplaint(
                    complaint, file, user.getId(), departmentId);

            model.addAttribute("complaintId", saved.getComplaintId());
            model.addAttribute("success", "Complaint submitted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Something went wrong!");
            return "complaint-form";
        }

        return "success";
    }

    // 🔹 View file
    @GetMapping("/files/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws Exception {

        Path path = Paths.get("uploads/").resolve(filename);
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok().body(resource);
    }
    
    
}

