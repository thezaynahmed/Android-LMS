package com.zain.lms.model;

public class getAllTeachers {
String teacherName;
String teacherEmail;
int teacherContact;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public int getTeacherContact() {
        return teacherContact;
    }

    public void setTeacherContact(int teacherContact) {
        this.teacherContact = teacherContact;
    }

    public getAllTeachers() {
    }

    public getAllTeachers(String teacherName, String teacherEmail, int teacherContact) {
        this.teacherName = teacherName;
        this.teacherEmail = teacherEmail;
        this.teacherContact = teacherContact;
    }
}
