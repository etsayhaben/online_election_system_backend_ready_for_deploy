package com.onlineelection.system.RegisterationService.Service;

import com.onlineelection.system.RegisterationService.Entity.Candidate;
import com.onlineelection.system.RegisterationService.Entity.CandidateReport;
import com.onlineelection.system.RegisterationService.Repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ElectionReportService {

    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private VoterService voterService;

    // Generate report for all campuses
    public Map<String, List<CandidateReport>> generateElectionReport() {
        // Fetch all candidates
        List<Candidate> candidates = candidateRepository.findAll();
        
        // Group candidates by campus
        Map<String, List<Candidate>> candidatesByCampus = candidates.stream()
            .collect(Collectors.groupingBy(Candidate::getCampus));

        Map<String, List<CandidateReport>> report = new HashMap<>();

        // Calculate votes and determine winners per campus
        for (Map.Entry<String, List<Candidate>> entry : candidatesByCampus.entrySet()) {
            String campus = entry.getKey();
            List<Candidate> campusCandidates = entry.getValue();

            // Sort candidates by votes in descending order
            List<Candidate> sortedCandidates = campusCandidates.stream()
                .sorted(Comparator.comparingInt(Candidate::getCounter).reversed())
                .collect(Collectors.toList());

            // Generate report for this campus
            report.put(campus, generateCampusReport(campus, sortedCandidates));
        }

        return report;
    }

    // Generate report for a specific campus
    public List<CandidateReport> generateElectionReportForCampus(String campus) {
        // Fetch candidates only for the specified campus
        List<Candidate> candidatesForCampus = candidateRepository.findByCampus(campus);

        if (candidatesForCampus.isEmpty()) {
            return Collections.emptyList();
        }

        // Generate and return report for the specified campus
        return generateCampusReport(campus, candidatesForCampus);
    }

    // Generate report based on studentId
    public List<CandidateReport> generateElectionReportForStudent(String studentId) {
        // Retrieve campus using studentId
        String campus = voterService.getCampusByStudentId(studentId); // You should implement this method
        
        if (campus == null) {
            throw new IllegalArgumentException("Campus not found for student ID: " + studentId);
        }

        // Call existing method to generate report for that campus
        return generateElectionReportForCampus(campus);
    }

    // Helper method to generate a report for a campus
    private List<CandidateReport> generateCampusReport(String campus, List<Candidate> campusCandidates) {
        List<CandidateReport> campusReport = new ArrayList<>();

        // Sort and add top candidates to the campus report
        for (int i = 0; i < campusCandidates.size() && i < 13; i++) {
            Candidate candidate = campusCandidates.get(i);
            String candidateId = candidate.getMemberOfParliament().getAccount().getStudentId();
            String fullName = voterService.getFullName(candidateId);
            campusReport.add(new CandidateReport(fullName, candidate.getCounter(), campus));
        }

        // Add the winner label for the highest-voted candidate
        if (!campusCandidates.isEmpty()) {
            Candidate winner = campusCandidates.get(0);
            campusReport.add(new CandidateReport("Winner", winner.getCounter(), campus));
        }

        return campusReport;
    }
}
