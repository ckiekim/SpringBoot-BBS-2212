package com.mulcam.bbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan		// Filter scan할 때 필요함
public class BbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BbsApplication.class, args);
	}

}
