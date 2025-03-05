package com.dilmah.hostel.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.FoodOrder;

@RepositoryRestResource(exported = false)
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
	List<FoodOrder> findByOrderedDateBetween(LocalDateTime start, LocalDateTime end);
	List<FoodOrder> findByUserUserID(String username);
	List<FoodOrder> findByUserUserIDAndOrderedDate(String userID, LocalDateTime orderedDate);
	Optional<FoodOrder> findByUserUserIDAndOrderTypeAndOrderedDate(String userID, String orderType, LocalDateTime orderedDate);
}
