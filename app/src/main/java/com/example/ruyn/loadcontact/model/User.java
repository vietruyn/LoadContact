package com.example.ruyn.loadcontact.model;

/**
 * Created by Admin on 1/10/2015.
 */
public class User {

    public  String username;
    public String password;
    public String fullname;
    public String number;

    public User() {
    }

    public User(String username, String password, String fullname, String number) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.number = number;
    }
}