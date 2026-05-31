package com.onlineelection.system.RegisterationService.Service;

import com.onlineelection.system.RegisterationService.DTO.NoticeDTO;
import com.onlineelection.system.RegisterationService.Entity.Notice;
import com.onlineelection.system.RegisterationService.Entity.NoticeRole;
import com.onlineelection.system.RegisterationService.Repository.NoticeRepository;
import com.onlineelection.system.RegisterationService.Repository.NoticeRoleRepository; // Import the NoticeRoleRepository
import com.onlineelection.system.UserModelService.Entity.Account;
import com.onlineelection.system.UserModelService.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private NoticeRoleRepository noticeRoleRepository; // Inject the NoticeRoleRepository

    @Autowired
    private AccountRepository accountRepository;

    public Notice createNotice(NoticeDTO noticeDTO) {
        String studentId = noticeDTO.getStudentId();
        Optional<Account> optionalAccount = accountRepository.findByStudentId(studentId);

        Account account = optionalAccount.orElseThrow(() ->
            new RuntimeException("Account not found for student ID: " + studentId)
        );

        Notice notice = new Notice();
        notice.setTitle(noticeDTO.getTitle());
        notice.setContent(noticeDTO.getContent());
        notice.setAuthor(account);

        List<NoticeRole> noticeRoles = new ArrayList<>();
        for (String role : noticeDTO.getTargetRoles()) {
            NoticeRole noticeRole = new NoticeRole(notice, role);
            noticeRoles.add(noticeRole);
        }
        notice.setNoticeRoles(noticeRoles);

        return noticeRepository.save(notice);
    }

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    public List<Notice> getNoticesByAuthorId(Long authorId) {
        return noticeRepository.findByAuthorId(authorId);
    }

    public List<Notice> getRecentNotices() {
        return noticeRepository.findAllNoticesOrderedByDate();
    }

    public Optional<Notice> getNoticeById(Long id) {
        return noticeRepository.findById(id);
    }

  
    public List<String> getNoticeContentsByRole(String role) {
        System.out.print("role received is"+role);
        System.out.print("data received"+noticeRoleRepository.findNoticeContentsByRole(role));
        return noticeRoleRepository.findNoticeContentsByRole(role);
    }
}
