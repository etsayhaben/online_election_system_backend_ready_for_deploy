package com.onlineelection.system.RegisterationService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.onlineelection.system.RegisterationService.Service.VoterService;
import java.util.*;
@RestController
@RequestMapping("/admin/voter")
public class VoterController {

    @Autowired
    private VoterService voterService;

    @GetMapping("/generate")
    public ResponseEntity<String> generateVoters() {
        voterService.generateVoters();
        return new ResponseEntity<>("2000 Voters generated successfully!", HttpStatus.OK);
    }

    @PostMapping("/vote")
    public ResponseEntity<String> submitVote(@RequestBody Map<String, String> voteData) {
        String studentId = voteData.get("studentId");

        Long campaignId;
        try {
            campaignId = Long.parseLong(voteData.get("campaignId"));
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid campaign ID format.", HttpStatus.BAD_REQUEST);
        }

        System.out.println("Received Vote:");
        System.out.println("Student ID: " + studentId);
        System.out.println("Campaign ID: " + campaignId);

        boolean success = voterService.submitVote(studentId, campaignId);

        if (success) {
            return new ResponseEntity<>("Vote submitted successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("you have already voted.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/hasVoted/{studentId}")
    public ResponseEntity<Boolean> hasVoted(@PathVariable String studentId) {
        boolean hasVoted = voterService.hasVoted(studentId);
        return new ResponseEntity<>(hasVoted, HttpStatus.OK);
    }
}
