package com.onlineelection.system.RegisterationService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineelection.system.RegisterationService.Entity.Candidate;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    
    // Method to find a single candidate by their associated MemberOfParliament ID
    Candidate findByMemberOfParliamentId(Long memberOfParliamentId);
    
    // Optional: Find all candidates by campus
    List<Candidate> findAllByCampus(String campus);

    // Optional: Find all candidates, this is usually provided by JpaRepository already
    // List<Candidate> findAll();  // Not necessary unless you want to customize it
List<Candidate> findByCampus(String campus);

}
