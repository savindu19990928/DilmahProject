package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dilmah.hostel.model.Room;

@RepositoryRestResource(path = "room")
@PreAuthorize("hasRole('WARDEN')")
public interface RoomRepository extends JpaRepository<Room, Long> {
}
