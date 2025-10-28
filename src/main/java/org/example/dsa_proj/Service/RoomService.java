package org.example.dsa_proj.Service;
import org.example.dsa_proj.Models.Room;
import org.example.dsa_proj.Rep.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RoomService {

    @Autowired
    private RoomRepo roomRepo;

    public static List<Room> getAllRooms() {
        return RoomRepo.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepo.findById(id);
    }

    public List<Room> getAvailableRoomsByType(String roomType) {
        return roomRepo.findByRoomTypeAndStatus(roomType, "Available");
    }

    public Room saveRoom(Room room) {
        return roomRepo.save(room);
    }

    public void updateOccupancy(Room room, int change) {
        room.setOccupancy(room.getOccupancy() + change);
        if (room.getOccupancy() >= room.getCapacity())
            room.setStatus("Full");
        else
            room.setStatus("Available");
        roomRepo.save(room);
    }
}

