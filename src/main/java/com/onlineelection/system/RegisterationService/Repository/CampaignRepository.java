package com.onlineelection.system.RegisterationService.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlineelection.system.RegisterationService.Entity.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    Campaign findByCampaignId(Long campaignId);
    
    Campaign findMemberOfParliamentByCampaignId(Long campaignId);
    
    @Query("SELECT c FROM Campaign c WHERE c.memberOfParliament.voter.campus = :campus")
    List<Campaign> findCandidatesByCampus(@Param("campus") String campus);
    
    @Query("SELECT c FROM Campaign c WHERE c.status = :status")
    List<Campaign> findByStatus(@Param("status") String status);
    
    // Fetch approved candidates by status and campus
    List<Campaign> findByStatusAndCampus(String status, String campus);
}
