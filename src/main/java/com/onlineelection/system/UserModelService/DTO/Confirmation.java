package com.onlineelection.system.UserModelService.DTO;

public class Confirmation{
    private String email;           // Email of the user
    private int confirmationCode;   // Confirmation code for verification
    private String studentId;       // Student ID of the user

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for confirmation code
    public int getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(int confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    // Getter and Setter for student ID
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
