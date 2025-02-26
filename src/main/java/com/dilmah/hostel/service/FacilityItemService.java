package com.dilmah.hostel.service;

import com.dilmah.hostel.model.FacilityItem;
import com.dilmah.hostel.model.BorrowedItem;
import com.dilmah.hostel.repository.FacilityItemRepository;
import com.dilmah.hostel.repository.BorrowedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityItemService {

    @Autowired
    private FacilityItemRepository facilityItemRepository;

    @Autowired
    private BorrowedItemRepository borrowedItemRepository;

    public List<FacilityItem> getAllFacilityItems() {
        return facilityItemRepository.findAll();
    }

    public Optional<FacilityItem> getFacilityItemById(int id) {
        return facilityItemRepository.findById(id);
    }

    public FacilityItem saveFacilityItem(FacilityItem facilityItem) {
        return facilityItemRepository.save(facilityItem);
    }

    public FacilityItem updateFacilityItem(int id, FacilityItem facilityItemDetails) {
        return facilityItemRepository.findById(id).map(facilityItem -> {
            facilityItem.setFacility(facilityItemDetails.getFacility());
            facilityItem.setName(facilityItemDetails.getName());
            facilityItem.setDescription(facilityItemDetails.getDescription());
            facilityItem.setQuantity(facilityItemDetails.getQuantity());
            return facilityItemRepository.save(facilityItem);
        }).orElseThrow(() -> new RuntimeException("FacilityItem not found with id " + id));
    }

    public void deleteFacilityItem(int id) {
        facilityItemRepository.deleteById(id);
    }

    public List<BorrowedItem> getAllBorrowedItems() {
        return borrowedItemRepository.findAll();
    }

    public Optional<BorrowedItem> getBorrowedItemById(int id) {
        return borrowedItemRepository.findById(id);
    }

    public BorrowedItem saveBorrowedItem(BorrowedItem borrowedItem) {
        return borrowedItemRepository.save(borrowedItem);
    }

    public BorrowedItem updateBorrowedItem(int id, BorrowedItem borrowedItemDetails) {
        return borrowedItemRepository.findById(id).map(borrowedItem -> {
            borrowedItem.setHostellerFacilityProfile(borrowedItemDetails.getHostellerFacilityProfile());
            borrowedItem.setFacilityItem(borrowedItemDetails.getFacilityItem());
            borrowedItem.setName(borrowedItemDetails.getName());
            borrowedItem.setDescription(borrowedItemDetails.getDescription());
            borrowedItem.setStatus(borrowedItemDetails.getStatus());
            borrowedItem.setReturnedAt(borrowedItemDetails.getReturnedAt());
            return borrowedItemRepository.save(borrowedItem);
        }).orElseThrow(() -> new RuntimeException("BorrowedItem not found with id " + id));
    }

    public void deleteBorrowedItem(int id) {
        borrowedItemRepository.deleteById(id);
    }

    public List<BorrowedItem> getBorrowedItemsByHostellerUserId(String userId, int facilityId) {
        return borrowedItemRepository.findByHostellerFacilityProfile_Hosteller_UserIDAndFacility_Id(userId, facilityId);
    }
}
