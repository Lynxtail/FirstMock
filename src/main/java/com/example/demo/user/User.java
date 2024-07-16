package com.example.demo.user;
import java.time.LocalDate;

public class User {
    public String login;
    public String password;
    public LocalDate date;

    public User(String login, String password){
        this.login = login;
        this.password = password;
        this.date = LocalDate.now();  
    }
}