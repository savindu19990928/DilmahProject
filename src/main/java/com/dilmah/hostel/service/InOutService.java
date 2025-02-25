package com.dilmah.hostel.service;

import com.dilmah.hostel.model.Hosteller;
import com.dilmah.hostel.model.InOut;
import com.dilmah.hostel.repository.InOutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InOutService {

    @Autowired
    private InOutRepository inOutRepository;

    // Create or Update InOut record
    public InOut saveOrUpdate(InOut inOut) {
        return inOutRepository.save(inOut);
    }

    // Find by ID
    public Optional<InOut> findById(long id) {
        return inOutRepository.findById(id);
    }

    // Find by otpIn
    public Optional<InOut> findByOtpIn(String otpIn) {
        return inOutRepository.findByOtp1(otpIn);
    }

    // Find by otpOut
    public Optional<InOut> findByOtpOut(String otpOut) {
        return inOutRepository.findByOtp2(otpOut);
    }

    // Get all records
    public List<InOut> findAll() {
        return inOutRepository.findAll();
    }

    // Delete by ID
    public void deleteById(long id) {
        inOutRepository.deleteById(id);
    }
    
    public List<InOut> findByHosteller(Optional<Hosteller> hosteller) {
        return inOutRepository.findByHosteller(hosteller);
    }
}
