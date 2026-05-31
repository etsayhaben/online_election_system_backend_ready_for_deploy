package com.onlineelection.system.RegisterationService.Entity; // Ensure this is the correct package declaration

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;

@Entity
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long campaignId;

    // Fields to store file paths instead of binary data
    private String candidatePhotoPath;
    private String candidateExperiencePath;

    @OneToOne
    @JoinColumn(name = "member_of_parliament_id")
    private MemberOfParliament memberOfParliament;

    private String status = "pending"; // Status field with a default value of "pending"

    private String description; // Description field

    // Add a campus field
    private String campus; // Campus field

    // Constructors
    public Campaign() {
    }

    public Campaign(String candidatePhotoPath, String candidateExperiencePath, MemberOfParliament memberOfParliament, String status, String description, String campus) {
        this.candidatePhotoPath = candidatePhotoPath;
        this.candidateExperiencePath = candidateExperiencePath;
        this.memberOfParliament = memberOfParliament;
        this.status = status;
        this.description = description;
        this.campus = campus; // Initialize the campus field
    }

    // Getters and Setters
    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCandidatePhotoPath() {
        return candidatePhotoPath;
    }

    public void setCandidatePhotoPath(String candidatePhotoPath) {
        this.candidatePhotoPath = candidatePhotoPath;
    }

    public String getCandidateExperiencePath() {
        return candidateExperiencePath;
    }

    public void setCandidateExperiencePath(String candidateExperiencePath) {
        this.candidateExperiencePath = candidateExperiencePath;
    }

    public MemberOfParliament getMemberOfParliament() {
        return memberOfParliament;
    }

    public void setMemberOfParliament(MemberOfParliament memberOfParliament) {
        this.memberOfParliament = memberOfParliament;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCampus() {
        return campus; // Getter for campus
    }

    public void setCampus(String campus) {
        this.campus = campus; // Setter for campus
    }
}
