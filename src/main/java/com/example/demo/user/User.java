package com.example.demo.user;

import java.sql.Date;

public class User {   
    public String login;
    public String password;
    public Date date;
    public String email;
    
    public User(String login, String password, Date date, String email){
        this.login = login;
        this.password = password;
        this.date = date;
        this.email = email;
    }
}