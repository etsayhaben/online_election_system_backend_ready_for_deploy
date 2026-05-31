package com.onlineelection.system.RegisterationService.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.onlineelection.system.RegisterationService.Entity.Campaign;
import com.onlineelection.system.RegisterationService.Entity.MemberOfParliament;
import com.onlineelection.system.RegisterationService.Entity.Candidate;
import com.onlineelection.system.RegisterationService.Repository.CampaignRepository;
import com.onlineelection.system.RegisterationService.Repository.MemberOfParlamentRepository;
import com.onlineelection.system.RegisterationService.Repository.CandidateRepository;
import com.onlineelection.system.RegisterationService.Repository.StudentRepository;
import com.onlineelection.system.UserModelService.Entity.Account;
import com.onlineelection.system.UserModelService.Repository.AccountRepository;
import com.onlineelection.system.UserModelService.Service.AccountService;

@Service
public class CandidateService {

    private final CampaignRepository campaignRepository;
    private final AccountService accountService;
    private final MemberOfParlamentRepository memberOfParlamentRepository;
    private final AccountRepository accountRepository;
    private final CandidateRepository candidateRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public CandidateService(MemberOfParlamentRepository memberOfParlamentRepository,
                            CampaignRepository campaignRepository, 
                            AccountService accountService, 
                            AccountRepository accountRepository,
                            CandidateRepository candidateRepository,
                            StudentRepository studentRepository) {
        this.campaignRepository = campaignRepository;
        this.accountService = accountService;
        this.candidateRepository = candidateRepository;
        this.memberOfParlamentRepository = memberOfParlamentRepository;
        this.accountRepository = accountRepository;
        this.studentRepository = studentRepository;
    }

    // Fetch all candidates with "pending" status
    public List<Campaign> getCandidates() {
        return campaignRepository.findByStatus("pending");
    }

    // Fetch all approved candidates
    public List<Campaign> getCandidatesApproved() {
        return campaignRepository.findByStatus("approved");
    }

    // Save a file to the specified directory
    private String saveFile(MultipartFile file, String directory) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Ensure the directory exists
        File dir = new File("public/" + directory);
        if (!dir.exists()) {
            dir.mkdirs(); // This will create the directory if it doesn't exist
        }

        // Generate a unique filename
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Path for storing the file
        Path path = Paths.get("public/" + directory + "/" + fileName);

        // Save the file
        Files.write(path, file.getBytes());

        return directory + "/" + fileName;
    }

    // Method to apply for a campaign
    @Transactional
    public String applyCampaign(String studentId, 
                                MultipartFile candidatePhoto, 
                                MultipartFile experience) throws IOException {
        if (studentId.isEmpty() || candidatePhoto.isEmpty() || experience.isEmpty()) {
            throw new IllegalArgumentException("All fields are required.");
        }

        // Check if the member of parliament exists by student ID
        MemberOfParliament memberOfParliament = memberOfParlamentRepository.findByAccountStudentId(studentId);
        if (memberOfParliament == null) {
            throw new IllegalArgumentException("No member of parliament found for student ID: " + studentId);
        }

        // Retrieve the campus using the student ID
        Optional<String> campusOptional = studentRepository.findCampusByStudentId(studentId);
        String campus = campusOptional.orElseThrow(() -> 
            new IllegalArgumentException("No campus found for student ID: " + studentId));

        // Save candidate files
        String photoPath = saveFile(candidatePhoto, "images");
        String experiencePath = saveFile(experience, "files"); 
        String status = "pending";
        String description = ""; // You might want to add a description

        // Save Campaign
        Campaign campaign = new Campaign(photoPath, experiencePath, 
                                          memberOfParliament, status, 
                                          description, campus); // Include campus
        campaignRepository.save(campaign);

        return "Campaign application submitted successfully.";
    }

    // Approve a candidate by updating their role and setting isCandidate to true
    @Transactional
    public boolean approveCandidate(Long campaignId) {
        // Log the campaignId for debugging
        System.out.println("Approving campaign with ID: " + campaignId);

        // Find the campaign by ID
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found for ID: " + campaignId));

        // Get the associated MemberOfParliament
        MemberOfParliament mp = campaign.getMemberOfParliament();
        if (mp == null) {
            throw new IllegalArgumentException("Member of Parliament not found for the campaign.");
        }

        // Set the isCandidate flag to true
        mp.setIsCandidate(true);
        campaign.setStatus("approved");

        try {
            // Save the updated MemberOfParliament
            memberOfParlamentRepository.save(mp); // Save the updated isCandidate flag
            System.out.println("isCandidate updated successfully to true for Member of Parliament.");

            Candidate existingCandidates = candidateRepository.findByMemberOfParliamentId(mp.getId());
if (existingCandidates == null) {  // Check for null instead of isEmpty()
    // Create and save the Candidate entity based on the MemberOfParliament
    Candidate candidate = new Candidate();
    candidate.setMemberOfParliament(mp);  // Set the associated MemberOfParliament
    candidate.setCounter(0);  // Initialize the vote counter to 0
    
    // Find the student ID from that MemberOfParliament ID
    String studentId = mp.getAccount().getStudentId();
    Optional<String> campusOptional = studentRepository.findCampusByStudentId(studentId);
    String campus = campusOptional.orElse("Default Campus"); // Handle absence as needed
    System.out.println("Campus: " + campus); // Log the campus
    candidate.setCampus(campus);

    // Save the new Candidate entity
    candidateRepository.save(candidate);
    System.out.println("Candidate entity created successfully for Member of Parliament ID: " + mp.getId());
} else {
    System.out.println("Candidate already exists for Member of Parliament ID: " + mp.getId());
}

        } catch (Exception e) {
            throw new RuntimeException("Failed to approve candidate for campaign ID: " + campaignId, e);
        }

        return true;
    }

    // Fetch candidates by campus
    public List<Campaign> getCandidatesByCampus(String campus) {
        return campaignRepository.findCandidatesByCampus(campus);
    }

// Fetch approved candidates by campus
public List<Campaign> getApprovedCandidates(String campus) {
    return campaignRepository.findByStatusAndCampus("approved", campus);
}



    // Reject a candidate
    @Transactional
    public boolean rejectCandidate(Long campaignId, String rejectReason) {
        System.out.println("Rejecting campaign with ID: " + campaignId + " with reason: " + rejectReason);

        // Find the campaign by ID
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new IllegalArgumentException("Campaign not found for ID: " + campaignId));

        // Update the description field with the rejection reason
        campaign.setDescription(rejectReason);
        campaign.setStatus("rejected");

        // Get the associated MemberOfParliament
        MemberOfParliament mp = campaign.getMemberOfParliament();
        if (mp == null) {
            throw new IllegalArgumentException("Member of Parliament not found for the campaign.");
        }

        // Set the isCandidate flag to false
        mp.setIsCandidate(false);

        try {
            // Save the updated campaign and MemberOfParliament entities
            campaignRepository.save(campaign);
            memberOfParlamentRepository.save(mp);
            System.out.println("Campaign description updated with rejection reason and isCandidate updated to false for Member of Parliament.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to reject candidate for campaign ID: " + campaignId, e);
        }

        return true;
    }
}
