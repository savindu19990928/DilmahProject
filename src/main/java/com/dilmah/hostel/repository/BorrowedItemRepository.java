package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.dilmah.hostel.model.BorrowedItem;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface BorrowedItemRepository extends JpaRepository<BorrowedItem, Integer> {
	List<BorrowedItem> findByHostellerFacilityProfile_Hosteller_UserIDAndFacility_Id(String userId, int facilityId);
}