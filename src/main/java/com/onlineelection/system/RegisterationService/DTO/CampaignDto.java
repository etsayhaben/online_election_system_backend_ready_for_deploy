package com.onlineelection.system.RegisterationService.DTO;

public class CampaignDto {

    private byte[] experience; // Changed type to byte array
    private int yearOfStudy;
    private byte[] candidatePhoto; // Changed type to byte array
    private String studentId;
    private String fullName;

    public CampaignDto() {
        // Default constructor
    }

    public CampaignDto(String fullName, byte[] experience, int yearOfStudy,
            byte[] candidatePhoto, String studentId) {

        this.experience = experience;
        this.yearOfStudy = yearOfStudy;
        this.candidatePhoto = candidatePhoto;
        this.studentId = studentId;
        this.fullName = fullName;
    }

    // Getters and setters

    public byte[] getExperience() {
        return experience;
    }

    public void setExperience(byte[] experience) {
        this.experience = experience;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public byte[] getCandidatePhoto() {
        return candidatePhoto;
    }

    public void setCandidatePhoto(byte[] candidatePhoto) {
        this.candidatePhoto = candidatePhoto;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
