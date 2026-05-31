package com.onlineelection.system.RegisterationService.Controller;

import com.onlineelection.system.RegisterationService.Entity.CandidateReport;
import com.onlineelection.system.RegisterationService.Service.ElectionReportService;
import com.onlineelection.system.RegisterationService.Service.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/election")
public class ElectionReportController {

    @Autowired
    private ElectionReportService electionReportService;
    @Autowired
    private VoterService voterService;

    // Endpoint to get the full election report
    @GetMapping("/report")
    public Map<String, List<CandidateReport>> getElectionReport() {
        return electionReportService.generateElectionReport();
    }

    // Endpoint to get the election report for a specific studentId
    @GetMapping("/report/student/{studentId}")
    public List<CandidateReport> getElectionReportForStudent(@PathVariable String studentId) {
        // Assuming you have a method to get the campus by studentId
        String campusName = voterService.getCampusByStudentId(studentId);
        
        // Now generate the report for the retrieved campus
        return electionReportService.generateElectionReportForCampus(campusName);
    }
}
