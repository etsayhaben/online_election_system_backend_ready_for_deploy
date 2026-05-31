package com.onlineelection.system.UserModelService.DTO;

public class CheckCredentialsDTO {
    private String studentId;
    private String email;

    public CheckCredentialsDTO() {} // No-argument constructor

    public CheckCredentialsDTO(String studentId, String email) {
        this.studentId = studentId;
        this.email = email;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
}
