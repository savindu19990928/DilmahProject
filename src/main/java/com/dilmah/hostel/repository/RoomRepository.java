package com.dilmah.hostel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dilmah.hostel.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
