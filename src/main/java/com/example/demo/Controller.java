package com.example.demo;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {

	private final AtomicLong counter = new AtomicLong();
    private final LocalDate localDate = LocalDate.now();

	@GetMapping("/get_request")
	public GetResponse getRequest() {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
		return new GetResponse(counter.incrementAndGet(), "some text");
	}

    @PostMapping(value = "/post_request", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    public PostResponse postRequest(@RequestBody LoginForm login) {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
        return new PostResponse(counter.incrementAndGet(), login.login(), login.password(), localDate);
    }
    
}