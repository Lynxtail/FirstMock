package com.example.demo.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.files.FileWorker;
import com.example.demo.queries.ConnectAndRunQueries;
import com.example.demo.user.User;

@RestController
public class Controller {
	@Autowired
	private ConnectAndRunQueries db;
	@Autowired
	private FileWorker file;

	@GetMapping("/get_request")
	public ResponseEntity<?> getRequest(@RequestParam String login) {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
		try {
			User user = db.select(login);
			file.saveToFile(user.toString());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (SQLException e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @PostMapping(value = "/post_request", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    public ResponseEntity<?> postRequest(@RequestBody Map<String,String> user) {
		
		Set<String> fields = Set.of("login", "password", "date", "email");
		if (!(user.keySet().equals(fields))){
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		else if (user.containsValue("")){
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}

		User out_user = new User(user.get("login"), 
				user.get("password"),
				Date.valueOf(user.get("date")),
				user.get("email"));

		db.insert(out_user);
		return ResponseEntity.ok(out_user);
    }
    
}