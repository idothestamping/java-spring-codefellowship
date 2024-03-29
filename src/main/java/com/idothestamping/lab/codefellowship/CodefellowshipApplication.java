package com.idothestamping.lab.codefellowship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

@SpringBootApplication
public class CodefellowshipApplication {

	@Bean
	public SpringSecurityDialect getSecurityDialect() {
		return new SpringSecurityDialect();
	}
	@Bean
	public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver, SpringSecurityDialect sec) {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		templateEngine.addDialect(sec); // Enable use of "sec"
		return templateEngine;
	}

	public static void main(String[] args) {
		SpringApplication.run(CodefellowshipApplication.class, args);
	}

}

// Example to use for visibility only to authenticated users:
//<div sec:authorize="isAuthenticated()">
//		Text visible only to authenticated users.
//</div>
