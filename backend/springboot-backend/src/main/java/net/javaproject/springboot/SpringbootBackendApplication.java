package net.javaproject.springboot;

import net.javaproject.springboot.model.Employee;
import net.javaproject.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringbootBackendApplication implements CommandLineRunner, WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public void run(String... args) throws Exception {

	}

	// CORS Configuration method
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")   // Allow all paths
				.allowedOrigins("*") // Allow all origins
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow all HTTP methods
				.allowedHeaders("*"); // Allow all headers
	}
}
