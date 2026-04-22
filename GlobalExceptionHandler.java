package com.project.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

import com.project.models.Complaint;
import com.project.models.User;
import com.project.services.ComplaintService;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(Model model) {
        model.addAttribute("error", "File size exceeded! Max 5MB allowed.");
        return "complaint-form";
    }
}
