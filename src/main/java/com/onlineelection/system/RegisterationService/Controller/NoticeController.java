package com.onlineelection.system.RegisterationService.Controller;

import com.onlineelection.system.RegisterationService.Entity.Notice;
import com.onlineelection.system.RegisterationService.Repository.NoticeRepository;
import com.onlineelection.system.RegisterationService.Service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.onlineelection.system.RegisterationService.DTO.NoticeDTO;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeRepository noticeRepository;

    // Endpoint to create a new notice
    @PostMapping
    public ResponseEntity<Notice> createNotice(@RequestBody NoticeDTO notice) {
        Notice createdNotice = noticeService.createNotice(notice);
        return ResponseEntity.ok(createdNotice);
    }

    // Endpoint to retrieve all notices
    @GetMapping
    public ResponseEntity<List<Notice>> getAllNotices() {
        List<Notice> notices = noticeService.getAllNotices();
        return ResponseEntity.ok(notices);
    }

    // Endpoint to retrieve notices by author ID
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Notice>> getNoticesByAuthor(@PathVariable Long authorId) {
        List<Notice> notices = noticeService.getNoticesByAuthorId(authorId);
        return ResponseEntity.ok(notices);
    }

    // Endpoint to retrieve notices in descending order by date (most recent first)
    @GetMapping("/recent")
    public ResponseEntity<List<Notice>> getRecentNotices() {
        List<Notice> recentNotices = noticeService.getRecentNotices();
        return ResponseEntity.ok(recentNotices);
    }

      @GetMapping("/role/{role}")
    public ResponseEntity<List<String>> getNoticesByRole(@PathVariable String role) {
        List<String> noticeContents = noticeService.getNoticeContentsByRole(role);
        return ResponseEntity.ok(noticeContents);
    }
}
