package com.onlineelection.system.RegisterationService.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notice_roles")
public class NoticeRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

    @Column(nullable = false)
    private String role;

    // Default constructor
    public NoticeRole() {
    }

    // Parameterized constructor
    public NoticeRole(Notice notice, String role) {
        this.notice = notice;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
