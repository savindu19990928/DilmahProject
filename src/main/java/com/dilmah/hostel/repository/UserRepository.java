package com.dilmah.hostel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dilmah.hostel.model.User;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByUserID(String userID);
}
