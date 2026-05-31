package com.onlineelection.system.RegisterationService.Controller;

import com.onlineelection.system.RegisterationService.Entity.Voter;
import com.onlineelection.system.RegisterationService.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/getCandidates")
public class DisplayCandidatesController {

    @Autowired
    private StudentRepository studentRepository;

    // Endpoint to get the campus of the student based on studentId
    @GetMapping("/campus/{studentId}")
    public String getCampusByStudentId(@PathVariable String studentId) {
        // Find the voter by studentId
        Voter voter = studentRepository.findByStudentId(studentId);

        if (voter != null) {
            // Return the campus of the student
            return voter.getCampus();
        } else {
            // Return a message if the student is not found
            return "Student not found or campus cannot be determined";
        }
    }
}
