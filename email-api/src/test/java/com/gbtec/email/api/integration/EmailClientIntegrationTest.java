package com.gbtec.email.api.integration;

import com.gbtec.email.api.EmailApiApplication;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EmailApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
class EmailClientIntegrationTest {

	private static final List<Integer> HOST_PORTS = Arrays.asList(8090, 8091, 8092);

	private static final String RETRIEVED_EMAIL_WITHOUT_PORT_MESSAGE = "retrieved email %d from the server with the port";

	private static final String RETRIEVED_EMAIL_MESSAGE = RETRIEVED_EMAIL_WITHOUT_PORT_MESSAGE + " %d";

	private final List<ConfigurableApplicationContext> applications = new ArrayList<>();

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@BeforeEach
	void startApps() {
		HOST_PORTS.forEach(port -> applications.add(startApp(port)));
	}

	@AfterEach
	void closeApps() {
		applications.forEach(ConfigurableApplicationContext::close);
	}

	@Test
	void loadBalancerEmailsIntegration() {
		final List<Integer> usedPorts = new ArrayList<>();
		for(int i = 1; i <= HOST_PORTS.size(); i++) {
			final ResponseEntity<String> response = testRestTemplate
					.getForEntity("http://localhost:" + port + "/emails/findById?id=" + i, String.class);
			BDDAssertions.then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
			BDDAssertions.then(response.getBody()).isNotNull();
			BDDAssertions.then(response.getBody()).startsWith(String.format(RETRIEVED_EMAIL_WITHOUT_PORT_MESSAGE, i));
			usedPorts.add(extractPort(response.getBody()));
		}
		BDDAssertions.then(usedPorts).containsAll(HOST_PORTS);
	}

	private ConfigurableApplicationContext startApp(int port) {
		return SpringApplication.run(TestApplication.class,
				"--server.port=" + port,
				"--local.server.port=" + port,
				"--spring.jmx.enabled=false");
	}

	private int extractPort(String response) {
		final String port = response.substring(response.lastIndexOf(" ") + 1);
		return Integer.parseInt(port);
	}

	@Configuration
	@EnableAutoConfiguration
	@RestController
	static class TestApplication {
		@LocalServerPort
		private int port;

		@RequestMapping(value = "/emailsapi/findById")
		public String findById(Long id) {
			return String.format(RETRIEVED_EMAIL_MESSAGE, id, port);
		}
	}
}