package com.onlineelection.system.RegisterationService.Entity;

public class CandidateReport {
    private String name;     // Candidate's name
    private int votes;       // Number of votes received
    private String campus;   // Campus of the candidate

    // Constructor
    public CandidateReport(String name, int votes, String campus) {
        this.name = name;
        this.votes = votes;
        this.campus = campus;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }
}
