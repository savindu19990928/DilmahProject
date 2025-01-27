package com.dilmah.hostel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dilmah.hostel.model.Room;
import com.dilmah.hostel.repository.RoomRepository;

import java.util.Optional;

@Service
public class RoomDetailsService {

    @Autowired
    private RoomRepository roomRepository;
    
    public Room findRoomByRoomNumber(Long roomNumber) {
        Optional<Room> room = roomRepository.findById(roomNumber);
        return room.orElse(null);
    }
    
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }
    
    public Room updateRoom(Room room) {
        Optional<Room> existingRoom = roomRepository.findById(room.getRoomNumber());

        if (existingRoom.isPresent()) {
            Room roomToUpdate = existingRoom.get();
            roomToUpdate.setStatus(room.getStatus());
            roomToUpdate.setAvailability(room.getAvailability());
            return roomRepository.save(roomToUpdate);
        } else {
            throw new IllegalArgumentException("Room updation failed");
        }
    }
    
    public void deleteRoomByRoomNumber(Long roomNumber) {
        roomRepository.deleteById(roomNumber);
    }
    
    public Iterable<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    
    public Room createRoom(Long roomNumber, Integer availability) {
        if (roomRepository.existsById(roomNumber)) {
            throw new IllegalArgumentException("Room with room number " + roomNumber + " already exists.");
        }
        
        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setAvailability(availability);
        
        return roomRepository.save(room);
    }
}
