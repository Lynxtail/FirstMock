package com.example.demo.controller;

import java.util.Map;
import java.util.Set;
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
    public ResponseEntity<?> postRequest(@RequestBody Map<String,String> user) {
		
		Set<String> fields = Set.of("login", "password");
		if (!(user.keySet().equals(fields))){
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		else if (user.get("login").isEmpty() || user.get("password").isEmpty()){
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
        return ResponseEntity.ok(new User(user.get("login"), user.get("password")));
    }
    
}