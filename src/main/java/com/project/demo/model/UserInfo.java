package com.project.demo.model;

import javax.persistence.Column;
import javax.persistence.Id;

public class UserInfo {

    @Id
    private String id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "address")
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return userName;
    }

    public void setUser_name(String user_name) {
        this.userName = user_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
