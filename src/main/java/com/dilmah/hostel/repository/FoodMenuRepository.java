package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.FoodMenu;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {

}
