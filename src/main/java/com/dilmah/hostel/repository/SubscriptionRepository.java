package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.Subscription;

@RepositoryRestResource(exported = false)
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

}
