package com.example.demo;
import java.time.LocalDate;

public record PostResponse(long id, String login, String password, LocalDate date) { }