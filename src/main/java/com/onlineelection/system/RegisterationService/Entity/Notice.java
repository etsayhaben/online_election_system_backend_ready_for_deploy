package com.onlineelection.system.RegisterationService.Entity;

import com.onlineelection.system.UserModelService.Entity.Account;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notices")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeRole> noticeRoles; // Relationship to NoticeRole

    @Column(nullable = false)
    private LocalDateTime date;

    @OneToOne
    @JoinColumn(name = "author_id", referencedColumnName = "studentId")
    private Account author;

    // Default constructor
    public Notice() {
        this.date = LocalDateTime.now(); // Automatically set the date when a notice is created
    }

    // Parameterized constructor
    public Notice(String title, String content, List<NoticeRole> noticeRoles, Account author) {
        this.title = title;
        this.content = content;
        this.noticeRoles = noticeRoles; // Set the noticeRoles
        this.date = LocalDateTime.now(); // Automatically set the date
        this.author = author;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<NoticeRole> getNoticeRoles() {
        return noticeRoles;
    }

    public void setNoticeRoles(List<NoticeRole> noticeRoles) {
        this.noticeRoles = noticeRoles;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }
}
