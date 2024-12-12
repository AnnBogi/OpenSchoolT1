package ru.t1.OpenSchoolT1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class OpenSchoolT1Application {

	public static void main(String[] args) {
		SpringApplication.run(OpenSchoolT1Application.class, args);
	}

}
