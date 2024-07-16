package com.example.demo.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.User;


@RestController
public class Controller {
	@GetMapping("/get_request")
	public ResponseEntity<?> getRequest() {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
		return new ResponseEntity<>("some text", HttpStatus.OK);
	}

    @PostMapping(value = "/post_request", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    public ResponseEntity<?> postRequest(@RequestBody User user) {
		System.out.println(String.format("\n\n\n%s\n%s\n%s\n\n\n", user.login, user.password, user.date));
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
		if (user.login == null || user.login.length() == 0){
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		if (user.password == null || user.password.length() == 0){
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
}