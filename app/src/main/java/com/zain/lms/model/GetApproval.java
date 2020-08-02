package com.zain.lms.model;

import com.android.volley.toolbox.StringRequest;

public class GetApproval {
    int id;
    String name;
    String contact;

    public GetApproval(int id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    public GetApproval() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GetApproval(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
