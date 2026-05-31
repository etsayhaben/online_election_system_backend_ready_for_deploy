package com.onlineelection.system.RegisterationService.Repository;

import com.onlineelection.system.RegisterationService.Entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Voter, Long> {

   

    // Method to find a campus by student_id
    @Query(value = "SELECT campus FROM voter_table WHERE student_id = :student_id", nativeQuery = true)
    Optional<String> findCampusByStudentId(@Param("student_id") String studentId);

    // Find voter(s) by email
    List<Voter> findByEmail(String email);

        Voter findByStudentId(@Param("student_id") String studentId);


   
}
