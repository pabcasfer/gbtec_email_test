package com.gbtec.email.business;

import com.gbtec.business.api.controller.EmailController;
import com.gbtec.business.application.model.EmailEntity;
import com.gbtec.business.application.model.EmailReceiverEntity;
import com.gbtec.business.application.model.EmailState;
import com.gbtec.business.application.repository.email.EmailRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("local1")
class TestDraft {

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private EmailController controller;

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	void prueba() {
		final EmailReceiverEntity receiver1 = EmailReceiverEntity.builder().emailTo("b@b.com").hidden(false).build();
		final EmailReceiverEntity receiver2 = EmailReceiverEntity.builder().emailTo("c@c.com").hidden(true).build();
		final EmailEntity email = EmailEntity.builder().uuid(1L).from("a@a.com").state(EmailState.DRAFT).receivers(Arrays.asList(receiver1, receiver2)).build();
		emailRepository.save(email);
		Optional<EmailEntity> byId = emailRepository.findById(1L);
		assert byId.isPresent();
		System.out.println(byId.get().getReceivers());
	}

	@Test
	@Transactional
	void prueba2() {
		controller.findById(1L);
	}

}
