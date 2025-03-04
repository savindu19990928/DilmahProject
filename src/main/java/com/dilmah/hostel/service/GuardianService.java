package com.dilmah.hostel.service;

import com.dilmah.hostel.model.Guardian;
import com.dilmah.hostel.repository.GuardianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuardianService {
    
    @Autowired
    private GuardianRepository guardianRepository;
    
    public Guardian saveGuardian(Guardian guardian) {
        return guardianRepository.save(guardian);
    }
    
    public Guardian getLatestGuardianByHostellerUserId(String userId) {
        List<Guardian> guardians = guardianRepository.findByHostellerUserIDOrderByAssignedDesc(userId);
        return guardians.isEmpty() ? null : guardians.get(0);
    }
}
