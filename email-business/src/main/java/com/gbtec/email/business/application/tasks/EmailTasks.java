package com.gbtec.email.business.application.tasks;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailEntityFilter;
import com.gbtec.email.business.application.model.EmailState;
import com.gbtec.email.business.application.service.email.EmailService;
import com.gbtec.utils.filters.FilterField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Component
public class EmailTasks {
	public static final String MARK_SPAM_TASK_CRON_CONFIG = "0 0 10 * * *";
	public static final String EMAIL_ADDRESS_SPAM = "carl@gbtec.es";

	@Autowired
	private EmailService service;

	@Scheduled(cron = MARK_SPAM_TASK_CRON_CONFIG)
	@Transactional
	public void markSpamEmails() {
		final EmailEntityFilter filter = EmailEntityFilter.builder()
				.froms(FilterField.of(Collections.singletonList(EMAIL_ADDRESS_SPAM)))
				// Should we update deleted and sending/sent emails???
				.states(FilterField.of(EmailState.notIn(EmailState.SPAM)))
				.build();
		final List<EmailEntity> emailsToMarkAsSpam = service.find(filter);
		if(!emailsToMarkAsSpam.isEmpty()) {
			emailsToMarkAsSpam.forEach(e -> e.setState(EmailState.SPAM));
			service.update(emailsToMarkAsSpam, false);
		}
	}
}