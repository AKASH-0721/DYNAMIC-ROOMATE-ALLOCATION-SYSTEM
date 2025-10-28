package org.example.dsa_proj.Service;


import org.example.dsa_proj.Models.RSR;
import org.example.dsa_proj.Rep.RoomSwapRepo;
import org.example.dsa_proj.Rep.RoomateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class RoomSwapService {

    @Autowired
    private RoomSwapRepo swapRepo;

    public RSR requestSwap(RSR req) {
        req.setRequestDate(LocalDate.now());
        req.setStatus("Pending");
        return swapRepo.save(req);
    }

    public List<RSR> getAllRequests() {
        return swapRepo.findAll();
    }

    public void updateStatus(Long id, String status) {
        RSR req = swapRepo.findById(id).orElseThrow();
        req.setStatus(status);
        swapRepo.save(req);
    }
}

