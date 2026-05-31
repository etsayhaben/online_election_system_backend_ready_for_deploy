package com.onlineelection.system.RegisterationService.DTO;

import java.util.List;

public class NoticeDTO {
    private String title;
    private String content;
    private String studentId; // Assuming this is how you retrieve the author's ID
    private List<String> targetRoles; // List of roles that should see this notice

    // Getters and Setters
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<String> getTargetRoles() {
        return targetRoles;
    }

    public void setTargetRoles(List<String> targetRoles) {
        this.targetRoles = targetRoles;
    }
}
