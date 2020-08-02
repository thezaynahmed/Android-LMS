package com.zain.lms.model;

public class Projects {
    String assignment_name_projects;
    String assignment_link_projects;

    public String getAssignment_name_projects() {
        return assignment_name_projects;
    }

    public void setAssignment_name_projects(String assignment_name_projects) {
        this.assignment_name_projects = assignment_name_projects;
    }

    public String getAssignment_link_projects() {
        return assignment_link_projects;
    }

    public void setAssignment_link_projects(String assignment_link_projects) {
        this.assignment_link_projects = assignment_link_projects;
    }

    public Projects() {
    }

    public Projects(String assignment_name_projects, String assignment_link_projects) {
        this.assignment_name_projects = assignment_name_projects;
        this.assignment_link_projects = assignment_link_projects;
    }
}
