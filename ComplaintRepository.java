package com.project.repo;


import com.project.models.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    Optional<Complaint> findByComplaintId(String complaintId);

    List<Complaint> findByUserId(Long userId);

    List<Complaint> findByDepartmentId(Long departmentId);
}