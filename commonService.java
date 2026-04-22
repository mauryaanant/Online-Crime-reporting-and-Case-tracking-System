package com.project.services;

import org.springframework.stereotype.Service;

@Service
public class commonService {

    public String getWelcomeMessage() {
        return "Welcome to Online Crime Reporting & Case Tracking System";
    }
}