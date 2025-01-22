package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.FoodOrder;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {

}
