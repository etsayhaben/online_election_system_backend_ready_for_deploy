package com.onlineelection.system.RegisterationService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;

@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_of_parliament_id", referencedColumnName = "id", nullable = false)
    private MemberOfParliament memberOfParliament; // Foreign key from MemberOfParliament

    @Column(nullable = false)
    private int counter = 0;  // Integer field to count votes, default value is 0
   private String campus;
    // Constructors
    public Candidate() {}

    public Candidate(MemberOfParliament memberOfParliament, int counter) {
        this.memberOfParliament = memberOfParliament;
        this.counter = counter;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setCampus(String campus)
    {
        this.campus=campus;
    }

public String getCampus()
{

return this.campus;
}

    public void setId(Long id) {
        this.id = id;
    }

    public MemberOfParliament getMemberOfParliament() {
        return memberOfParliament;
    }

    public void setMemberOfParliament(MemberOfParliament memberOfParliament) {
        this.memberOfParliament = memberOfParliament;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    // Method to increment the vote counter
    public void incrementCounter() {
        this.counter++;
    }
}
