package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.BorrowedItem;

public interface BorrowedItemRepository extends JpaRepository<BorrowedItem, Integer> {

}
