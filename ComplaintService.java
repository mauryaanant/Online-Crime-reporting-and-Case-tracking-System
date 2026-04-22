package com.project.services;

import com.project.models.Complaint;
import com.project.models.User;
import com.project.models.Department;
import com.project.repo.ComplaintRepository;
import com.project.repo.UserRepository;
import com.project.repo.DepartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private final Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();

    // 🔥 REGISTER COMPLAINT
    public Complaint registerComplaint(
            Complaint complaint,
            MultipartFile file,
            Long userId,
            Long departmentId) throws IOException {

        // ✅ 1. Generate Unique Complaint ID
        complaint.setComplaintId(UUID.randomUUID().toString());

        // ✅ 2. Default Status
        complaint.setStatus("PENDING");

        // ✅ 3. Auto Priority Logic
        complaint.setPriority(assignPriority(complaint.getCategory()));

        // ✅ 4. File Upload
        if (file != null && !file.isEmpty()) {

            // Prevent null filename
            String originalName = file.getOriginalFilename();
            if (originalName == null) {
                throw new RuntimeException("Invalid file name");
            }

            // Prevent path traversal attack
            originalName = Paths.get(originalName).getFileName().toString();

            String fileName = UUID.randomUUID() + "_" + originalName;

            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            complaint.setEvidencePath(fileName);
        }

        // ✅ 5. Map User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        complaint.setUser(user);

        // ✅ 6. Map Department
        Department dept = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        complaint.setDepartment(dept);

        // ✅ 7. Save
        return complaintRepository.save(complaint);
    }

    // 🔥 REUSABLE METHOD (ADMIN)
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    // 🔥 UPDATE STATUS
    public void updateStatus(Long id, String status) {
        Complaint complaint = complaintRepository.findById(id).orElse(null);
        if (complaint != null) {
            complaint.setStatus(status);
            complaintRepository.save(complaint);
        }
    }

    // 🔥 UPDATE PRIORITY
    public void updatePriority(Long id, String priority) {
        Complaint complaint = complaintRepository.findById(id).orElse(null);
        if (complaint != null) {
            complaint.setPriority(priority);
            complaintRepository.save(complaint);
        }
    }

    // 🔥 PRIORITY LOGIC (CLEAN METHOD)
    private String assignPriority(String category) {

        if (category == null) return "LOW";

        category = category.trim().toLowerCase();

        if (category.contains("fraud") || category.contains("cyber")) {
            return "HIGH";
        } else if (category.contains("theft")) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }
}