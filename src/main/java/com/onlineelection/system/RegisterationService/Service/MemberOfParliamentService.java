package com.onlineelection.system.RegisterationService.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineelection.system.RegisterationService.Entity.MemberOfParliament;
import com.onlineelection.system.RegisterationService.Repository.MemberOfParlamentRepository;

@Service
public class MemberOfParliamentService {

    private final MemberOfParlamentRepository memberOfParliamentRepository;

    @Autowired
    public MemberOfParliamentService(MemberOfParlamentRepository memberOfParliamentRepository) {
        this.memberOfParliamentRepository = memberOfParliamentRepository;
    }

    public List<String> getStudentIdsByMpIds(List<Long> mpIds) {
        // Fetch MemberOfParliament entities by their IDs
        List<MemberOfParliament> members = memberOfParliamentRepository.findAllById(mpIds);
        
        // Extract student IDs from the members' accounts
        return members.stream()
                .map(member -> member.getAccount() != null ? member.getAccount().getStudentId() : null) // Ensure account is not null
                .filter(studentId -> studentId != null) // Filter out null values
                .collect(Collectors.toList());
    }
}
