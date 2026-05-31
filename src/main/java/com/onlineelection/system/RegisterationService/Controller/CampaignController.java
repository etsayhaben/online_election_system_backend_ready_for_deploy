package com.onlineelection.system.RegisterationService.Controller;

import java.io.IOException;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.onlineelection.system.RegisterationService.DTO.CampaignDto;
import com.onlineelection.system.RegisterationService.Entity.Campaign;
import com.onlineelection.system.RegisterationService.Service.CandidateService;
import com.onlineelection.system.RegisterationService.Service.VoterService;
import com.onlineelection.system.UserModelService.Entity.Account;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    private final CandidateService candidateService;
    private final VoterService voterService;


    @Autowired
    public CampaignController(CandidateService candidateService,VoterService voterService) {
        this.candidateService = candidateService;
        this.voterService=voterService;
    }

   @PostMapping("/apply")
public ResponseEntity<?> applyCampaign(
        @RequestParam("studentId") String studentId,
        @RequestParam("candidatePhoto") MultipartFile candidatePhoto,
        @RequestParam("experience") MultipartFile experience) {

    // Validate the applier's personal information
    boolean isFound = voterService.checkExistingStudentId(studentId);
    String campus=voterService.getCampusByStudentId(studentId);

    // If not found, return error response
    if (!isFound) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Incorrect personal information. Please check your student ID, name, or year of study.");
    }

    try {
        // Proceed with account creation or campaign application
        String uploadResult = candidateService.applyCampaign(
                studentId,candidatePhoto, experience);
        return ResponseEntity.status(HttpStatus.OK).body(uploadResult);
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error uploading files: " + e.getMessage());
    }
}


    @GetMapping("getCandidates")
    public List<Campaign> getCandidates() {
        return candidateService.getCandidates();
    }
    @GetMapping("getCandidatesforVoter")
    public List<Campaign> getCandidatesforVoter() {
        return candidateService.getCandidatesApproved();
    }



  @GetMapping("/getCandidatesByCampus")
public ResponseEntity<?> getCandidatesByStudentId(@RequestParam("studentId") String studentId) {
    // Fetch the student's campus using the studentId

    String campus = voterService.getCampusByStudentId(studentId);
    System.out.print(campus);
    if (campus == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campus not found for the provided student ID.");
    }
                  

    // Fetch the candidates for that campus
    List<Campaign> candidates = candidateService.getApprovedCandidates(campus);

    System.out.print(candidates);
    return ResponseEntity.ok(candidates);
}

}
