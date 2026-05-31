
package com.onlineelection.system.RegisterationService.DTO;

public class CampaignWithStudentId {
    private Long campaignId;
    private String candidatePhotoPath;
    private String studentId;

    public CampaignWithStudentId(Long campaignId, String candidatePhotoPath, String studentId) {
        this.campaignId = campaignId;
        this.candidatePhotoPath = candidatePhotoPath;
        this.studentId = studentId; 
    }

    // Getter for campaignId
    public Long getCampaignId() {
        return campaignId;
    }

    // Setter for campaignId
    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    // Getter for candidatePhotoPath
    public String getCandidatePhotoPath() {
        return candidatePhotoPath;
    }

    // Setter for candidatePhotoPath
    public void setCandidatePhotoPath(String candidatePhotoPath) {
        this.candidatePhotoPath = candidatePhotoPath;
    }

    // Getter for studentId
    public String getStudentId() {
        return studentId;
    }

    // Setter for studentId
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "CampaignWithStudentId{" +
                "campaignId=" + campaignId +
                ", candidatePhotoPath='" + candidatePhotoPath + '\'' +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
