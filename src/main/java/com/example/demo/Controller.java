package com.example.demo;

import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


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
    public ResponseEntity<?> postRequest(@RequestBody User login) {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
        return new ResponseEntity<User>(login, HttpStatus.OK);
    }
    
}