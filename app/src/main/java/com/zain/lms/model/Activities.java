package com.zain.lms.model;

public class Activities {
    String assignment_name_activities;
    String assignment_link_activities;

    public String getAssignment_name_activities() {
        return assignment_name_activities;
    }

    public void setAssignment_name_activities(String assignment_name_activities) {
        this.assignment_name_activities = assignment_name_activities;
    }

    public String getAssignment_link_activities() {
        return assignment_link_activities;
    }

    public void setAssignment_link_activities(String assignment_link_activities) {
        this.assignment_link_activities = assignment_link_activities;
    }

    public Activities() {
    }

    public Activities(String assignment_name_activities, String assignment_link_activities) {
        this.assignment_name_activities = assignment_name_activities;
        this.assignment_link_activities = assignment_link_activities;
    }
}
