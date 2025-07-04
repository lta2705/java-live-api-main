package com.example.live;

//import com.example.live.service.ProducerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableCaching
@EnableJms
public class LiveApplication {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		SpringApplication.run(LiveApplication.class, args);
	}

}
