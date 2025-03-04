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
		Optional<FoodOrder> existingOrder = foodOrderRepository.findByUserUserIDAndOrderTypeAndOrderedDate(
				foodOrder.getUser().getUserID(), foodOrder.getOrderType(), foodOrder.getOrderedDate());

		if (existingOrder.isPresent()) {
			throw new RuntimeException("A food order for " + foodOrder.getOrderType() + " already exists on "
					+ foodOrder.getOrderedDate());
		}

		return foodOrderRepository.save(foodOrder);
	}

	public FoodOrder updateFoodOrder(long id, FoodOrder updatedFoodOrder) {
		return foodOrderRepository.findById(id).map(foodOrder -> {
			foodOrder.setOrderType(updatedFoodOrder.getOrderType());
			foodOrder.setOrderedDate(updatedFoodOrder.getOrderedDate());
			foodOrder.setUser(updatedFoodOrder.getUser());
			return foodOrderRepository.save(foodOrder);
		}).orElse(null);
	}

	public void deleteFoodOrder(long id) {
		foodOrderRepository.deleteById(id);
	}

	public FoodOrder getFirstFoodOrderByDateAndUser(String userID, LocalDateTime orderedDate) {
		return foodOrderRepository.findTopByUserUserIDAndOrderedDateOrderByOrderIDAsc(userID, orderedDate).orElseThrow(
				() -> new RuntimeException("No food order found for user " + userID + " on " + orderedDate));
	}
}