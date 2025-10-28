package org.example.dsa_proj.Rep;
import org.example.dsa_proj.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {

    List<Room> findByStatus(String status);

    List<Room> findByHostelBlock(String hostelBlock);

    // Find available rooms of specific type
    List<Room> findByRoomTypeAndStatus(String roomType, String status);
}
