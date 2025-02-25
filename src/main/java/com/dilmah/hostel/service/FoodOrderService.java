package com.dilmah.hostel.service;

import com.dilmah.hostel.model.FoodOrder;
import com.dilmah.hostel.repository.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FoodOrderService {

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    public List<FoodOrder> getAllFoodOrders() {
        return foodOrderRepository.findAll();
    }

    public Optional<FoodOrder> getFoodOrderById(long id) {
        return foodOrderRepository.findById(id);
    }

    public List<FoodOrder> getFoodOrdersByDate(LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = date.toLocalDate().atTime(23, 59, 59);
        return foodOrderRepository.findByOrderedDateBetween(startOfDay, endOfDay);
    }
    
    public List<FoodOrder> getFoodOrdersByUsername(String username) {
    	return foodOrderRepository.findByUserUserID(username);
    }

    public FoodOrder saveFoodOrder(FoodOrder foodOrder) {
        return foodOrderRepository.save(foodOrder);
    }

    public FoodOrder updateFoodOrder(long id, FoodOrder updatedFoodOrder) {
        return foodOrderRepository.findById(id).map(foodOrder -> {
            foodOrder.setOrderType(updatedFoodOrder.getOrderType());
            foodOrder.setOrderedDate(updatedFoodOrder.getOrderedDate());
            foodOrder.setUser(updatedFoodOrder.getUser());
            foodOrder.setFees(updatedFoodOrder.getFees());
            return foodOrderRepository.save(foodOrder);
        }).orElse(null);
    }

    public void deleteFoodOrder(long id) {
        foodOrderRepository.deleteById(id);
    }
}