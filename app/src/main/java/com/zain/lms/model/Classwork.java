package com.zain.lms.model;

public class Classwork {
    String assignment_name_classwork;
    String assignment_link_classwork;

    public Classwork() {
    }
    public Classwork(String assignment_name_classwork, String assignment_link_classwork) {
        this.assignment_name_classwork = assignment_name_classwork;
        this.assignment_link_classwork = assignment_link_classwork;
    }

    public String getAssignment_name_classwork() {
        return assignment_name_classwork;
    }

    public void setAssignment_name_classwork(String assignment_name_classwork) {
        this.assignment_name_classwork = assignment_name_classwork;
    }

    public String getAssignment_link_classwork() {
        return assignment_link_classwork;
    }

    public void setAssignment_link_classwork(String assignment_link_classwork) {
        this.assignment_link_classwork = assignment_link_classwork;
    }
}
