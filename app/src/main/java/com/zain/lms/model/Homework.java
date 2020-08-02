package com.zain.lms.model;

public class Homework {
    String assignment_name_homework;
    String assignment_link_homework;

    public Homework() {
    }

    public Homework(String assignment_name_homework, String assignment_link_homework) {
        this.assignment_name_homework = assignment_name_homework;
        this.assignment_link_homework = assignment_link_homework;
    }

    public String getAssignment_name_homework() {
        return assignment_name_homework;
    }

    public void setAssignment_name_homework(String assignment_name_homework) {
        this.assignment_name_homework = assignment_name_homework;
    }

    public String getAssignment_link_homework() {
        return assignment_link_homework;
    }

    public void setAssignment_link_homework(String assignment_link_homework) {
        this.assignment_link_homework = assignment_link_homework;
    }
}
