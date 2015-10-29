package com.example.ruyn.loadcontact.model;

/**
 * Created by Admin on 23/10/2015.
 */
public class Friend {
    public String fullName;
    public String phoneNumber;
    public String email;
    public int type;


    public Friend() {
    }

    public Friend(String fullName, String phoneNumber, String email, int type) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.type = type;
    }
}
