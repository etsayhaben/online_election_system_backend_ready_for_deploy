package com.onlineelection.system.RegisterationService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onlineelection.system.RegisterationService.Entity.Voter;
import com.onlineelection.system.RegisterationService.Repository.StudentRepository;
import com.onlineelection.system.RegisterationService.Repository.CampaignRepository;
import com.onlineelection.system.RegisterationService.Repository.CandidateRepository;
import com.onlineelection.system.RegisterationService.Entity.Candidate;
import com.onlineelection.system.RegisterationService.Entity.Campaign;
import jakarta.transaction.Transactional;
import java.util.Random;
import java.util.List;
@Service
public class VoterService {

    @Autowired
    private StudentRepository voterRepository;
    @Autowired 
    CandidateRepository candidateRepository;
    @Autowired
    private CampaignRepository campaignRepository;
   public boolean checkExistingStudentId(String studentId) {
    Voter voterData = voterRepository.findByStudentId(studentId);
    boolean exists = voterData != null; // Returns true if a voter with the student ID is found
    System.out.print("ID found in voter: " + exists);
    return exists;
}
public String getCampusByStudentId(String studentId) {
    Voter voter = voterRepository.findByStudentId(studentId);
    
    if (voter != null) {
        // Return the campus if the voter is found
        return voter.getCampus();
    }
    
    // Return null or an appropriate message if no voter is found with the given student ID
    return null; // or return "Student not found or campus cannot be determined";
}




    // Method to check if an email exists in the voter table
    public boolean checkExistingEmail(String email) {
        // Assuming you have a method in StudentRepository to find by email
        List<Voter> voterData = voterRepository.findByEmail(email);
        System.out.print("the email is"+email);

        return !voterData.isEmpty(); // Returns true if the email is found
    }

public Voter getDataFromVoter(String studentId) {
    return voterRepository.findByStudentId(studentId);
}

public List<Voter> getAllVoters() {
    return voterRepository.findAll();
}

public String getFullName(String studentId)
{
    Voter voter=getDataFromVoter(studentId);
    String firstName=voter.getFirstName();
    String lastName=voter.getLastName();
    String fullName=firstName+" "+lastName;
    return fullName;
}
public boolean hasVoted(String studentId) {
    Voter voter = voterRepository.findByStudentId(studentId);
    
    if (voter == null) {
        System.out.println("Voter not found with student ID: " + studentId);
        return false; // Return false if the voter is not found
    }
    
    return voter.getHasVoted(); // Returns true if the voter has voted, false otherwise
}


@Transactional
public boolean submitVote(String studentId, Long campaignId) {
    // Retrieve the voter by student ID
    Voter voter = voterRepository.findByStudentId(studentId);
    if (voter == null) {
        System.out.println("Voter not found with student ID: " + studentId);
        return false; // Return false if voter not found
    }

    // Check if the voter has already voted
    if (voter.getHasVoted()) {
        System.out.println("Voter has already voted.");
        return false; // Return false if voter has already voted
    }

    // Set hasVoted to true and save the updated voter
    voter.setHasVoted(true);
    voterRepository.save(voter); 

    // Retrieve the campaign by campaign ID
    Campaign campaign = campaignRepository.findByCampaignId(campaignId);
    if (campaign == null) {
        System.out.println("Campaign not found with ID: " + campaignId);
        return false; // Return false if campaign not found
    }

    // Get the Member of Parliament ID associated with the campaign
    Long memberOfParliamentId = campaign.getMemberOfParliament().getId();

    // Retrieve the candidate by Member of Parliament ID
    Candidate candidate = candidateRepository.findByMemberOfParliamentId(memberOfParliamentId);
    if (candidate == null) {
        System.out.println("Candidate not found for Member of Parliament ID: " + memberOfParliamentId);
        return false; // Return false if candidate not found
    }

    // Increment the candidate's vote count
    candidate.setCounter(candidate.getCounter() + 1);
    candidateRepository.save(candidate); // Save the updated candidate

    System.out.println("Vote submitted by student ID: " + studentId + " for campaign ID: " + campaignId);
    return true; // Return true for successful submission
}


    @Transactional
    public void generateVoters() {
        Random random = new Random();

        for (int i = 0; i < 2000; i++) {
            Voter voter = new Voter();

            String campus = CAMPUSES[random.nextInt(CAMPUSES.length)];
            voter.setCampus(campus);
            voter.setFirstName(FIRST_NAMES[random.nextInt(FIRST_NAMES.length)]);
            voter.setLastName(LAST_NAMES[random.nextInt(LAST_NAMES.length)]);
            voter.setEmail(EMAILS[random.nextInt(EMAILS.length)]);

            String studentId;
            if (campus.equals("quiha")) {
                studentId = "quiha" + (100000 + random.nextInt(900000)) + (random.nextInt(7) + 10);
            } else if (campus.equals("MIT")) {
                studentId = "MIT" + (100000 + random.nextInt(900000)) + (random.nextInt(7) + 10);
            } else if (campus.equals("business")) {
                studentId = "business" + (100000 + random.nextInt(900000)) + (random.nextInt(7) + 10);
            } else {
                studentId = "main" + (100000 + random.nextInt(900000)) + (random.nextInt(7) + 10);
            }
            voter.setStudentId(studentId);
            voter.setGrade(2.5 + random.nextDouble() * 1.5);
            voter.setYearOfStudy(random.nextInt(5) + 1);
            voter.setSection(random.nextInt(3) + 1);

            // Assign department based on campus
            switch (campus) {
                case "main campus":
                    voter.setDepartment(DEPARTMENTS_MAIN[random.nextInt(DEPARTMENTS_MAIN.length)]);
                    break;
                case "quiha":
                    voter.setDepartment(DEPARTMENTS_QUIHA[random.nextInt(DEPARTMENTS_QUIHA.length)]);
                    break;
                case "ayder":
                    voter.setDepartment(DEPARTMENTS_AYDER[random.nextInt(DEPARTMENTS_AYDER.length)]);
                    break;
                case "MIT":
                    voter.setDepartment(DEPARTMENTS_MIT[random.nextInt(DEPARTMENTS_MIT.length)]);
                    break;
                default:
                    voter.setDepartment(VET_DEPARTMENT);
                    break;
            }

            // Save the voter
            voterRepository.save(voter);
        }
    }

    // Define the constants used in the generateVoters method
    private static final String[] FIRST_NAMES = {"Abeba", "Alem", "Bekele", "Genet", "Hana", "Kebede", "Lemlem", "Mulu", "Selam", "Yonas"};
    private static final String[] LAST_NAMES = {"Bekele", "Abebe", "Kebede", "Yonas", "Melaku", "Gebre", "Tesfaye", "Amanuel", "Girma", "Teshome"};
    private static final String[] EMAILS = {"etsayhaben@gmail.com", "etsayhaben1992@gmail.com"};
    private static final String[] CAMPUSES = {"ayder", "main campus", "quiha", "business", "veternary", "MIT"};
    private static final String[] DEPARTMENTS_MAIN = {"agro economics", "horticulture", "food science", "physics", "biology"};
    private static final String[] DEPARTMENTS_QUIHA = {"software engineering", "electrical engineering", "computer science", "mechanical engineering", "civil engineering"};
    private static final String[] DEPARTMENTS_AYDER = {"medicine", "health officer", "pharmacy", "nurse"};
    private static final String[] DEPARTMENTS_MIT = {"electrical engineering", "computer science", "Information technology"};
    private static final String VET_DEPARTMENT = "veternary medicine";
}
