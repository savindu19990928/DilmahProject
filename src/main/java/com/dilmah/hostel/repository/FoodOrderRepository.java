package com.dilmah.hostel.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.FoodOrder;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
	List<FoodOrder> findByOrderedDateBetween(LocalDateTime start, LocalDateTime end);

}
