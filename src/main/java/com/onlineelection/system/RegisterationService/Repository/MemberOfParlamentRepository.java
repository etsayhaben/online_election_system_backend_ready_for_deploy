package com.onlineelection.system.RegisterationService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlineelection.system.RegisterationService.Entity.MemberOfParliament;
import com.onlineelection.system.UserModelService.Entity.Account;

import java.util.List;

@Repository
public interface MemberOfParlamentRepository extends JpaRepository<MemberOfParliament, Long> {
    
    // Method to find a MemberOfParliament by associated Account
    MemberOfParliament findByAccount(Account account);
    
    // Method to find a MemberOfParliament by the associated Account's student ID
    MemberOfParliament findByAccountStudentId(@Param("studentId") String studentId);

    // Optional: Method to find all MemberOfParliament by a list of student IDs
    List<MemberOfParliament> findByAccountStudentIdIn(@Param("studentIds") List<String> studentIds);
}
