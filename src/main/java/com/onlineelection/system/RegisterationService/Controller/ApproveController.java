package com.onlineelection.system.RegisterationService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
    import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

import com.onlineelection.system.RegisterationService.Service.CandidateService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/admin")
public class ApproveController {

    @Autowired
    private CandidateService candidateService;

    // Approve a campaign by updating its status to "approved"
    @PutMapping("/approveCampaign/{campaignId}")
    public ResponseEntity<String> approveCampaign(@PathVariable Long campaignId) {
        try {
            boolean approved = candidateService.approveCandidate(campaignId);
            if (approved) {
                return ResponseEntity.ok("Campaign approved successfully.");
            } else {
                return ResponseEntity.badRequest().body("Failed to approve campaign.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error approving campaign: " + e.getMessage());
        }
    }


@PutMapping("/rejectCampaign/{campaignId}")
public ResponseEntity<String> rejectCampaign(@PathVariable Long campaignId, @RequestBody Map<String, String> requestBody) {
    try {
        String reason = requestBody.get("reason");
        System.out.print("this is the reason"+reason);
        boolean rejected = candidateService.rejectCandidate(campaignId, reason);
        if (rejected) {
            return ResponseEntity.ok("Campaign rejected successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to reject campaign.");
        }
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error rejecting campaign: " + e.getMessage());
    }
}

}
