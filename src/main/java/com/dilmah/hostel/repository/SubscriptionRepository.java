package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

}
