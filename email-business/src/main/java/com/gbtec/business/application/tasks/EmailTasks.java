package com.gbtec.business.application.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailTasks {
	public static final String MARK_SPAM_TASK_CRON_CONFIG = "0 0 10 * * *";

	@Scheduled(cron = MARK_SPAM_TASK_CRON_CONFIG)
	public void markSpamEmails() {
		// TODO: Call the service so we mark the emails from carl@gbtec.es as spam
	}
}