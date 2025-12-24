package org.example.dsa_proj.Service;


import org.example.dsa_proj.Models.RoomSwapRequest;
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

    public RoomSwapRequest requestSwap(RoomSwapRequest req) {
        req.setRequestDate(LocalDate.now());
        req.setStatus("Pending");
        return swapRepo.save(req);
    }

    public List<RoomSwapRequest> getAllRequests() {
        return swapRepo.findAll();
    }

    public void updateStatus(Long id, String status) {
        RoomSwapRequest req = swapRepo.findById(id).orElseThrow();
        req.setStatus(status);
        swapRepo.save(req);
    }
    
    /**
     * Get pending swap requests ordered by priority
     * @return List of pending requests
     */
    public List<RoomSwapRequest> getPendingRequests() {
        return swapRepo.findByStatusOrderByRequestDateAsc("Pending");
    }
    
    /**
     * Process a swap request
     * @param requestId Request ID
     * @param approved Whether to approve or reject
     * @param processedBy Admin who processed the request
     * @param notes Additional notes
     */
    public void processSwapRequest(Long requestId, boolean approved, String processedBy, String notes) {
        RoomSwapRequest request = swapRepo.findById(requestId).orElse(null);
        if (request != null) {
            if (approved) {
                request.approve(processedBy, notes);
            } else {
                request.reject(processedBy, notes);
            }
            swapRepo.save(request);
        }
    }
}

