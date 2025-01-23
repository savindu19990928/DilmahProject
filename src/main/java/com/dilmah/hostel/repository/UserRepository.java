package com.dilmah.hostel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilmah.hostel.model.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUserID(String userID);
}
