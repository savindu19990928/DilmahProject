package com.dilmah.hostel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilmah.hostel.model.Fine;
import com.dilmah.hostel.repository.FineRepository;

@Service
public class FineService {

    @Autowired
    private FineRepository fineRepository;

    // Create a new fine
    public Fine createFine(Fine fine) {
        return fineRepository.save(fine);
    }

    // Get all fines
    public List<Fine> getAllFines() {
        return fineRepository.findAll();
    }

    // Get fine by ID
    public Optional<Fine> getFineById(long fineID) {
        return fineRepository.findById(fineID);
    }

    // Get fines for a specific hosteller
    public List<Fine> getFinesByHostellerId(String hostellerID) {
        return fineRepository.findByHostellerUserID(hostellerID);
    }

    // Update a fine
    public Fine updateFine(long fineID, Fine fineDetails) {
        return fineRepository.findById(fineID).map(fine -> {
            fine.setAmount(fineDetails.getAmount());
            fine.setReason(fineDetails.getReason());
            fine.setPaid(fineDetails.isPaid());
            return fineRepository.save(fine);
        }).orElseThrow(() -> new RuntimeException("Fine not found"));
    }

    // Mark a fine as paid
    @Transactional
    public Fine markFineAsPaid(long fineID) {
        Fine fine = fineRepository.findById(fineID)
                .orElseThrow(() -> new RuntimeException("Fine not found"));
        fine.setPaid(true);
        return fineRepository.save(fine);
    }

    // Delete a fine
    public void deleteFine(long fineID) {
        fineRepository.deleteById(fineID);
    }
}
