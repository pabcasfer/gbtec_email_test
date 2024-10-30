package com.gbtec.email.business.application.tasks;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailState;
import com.gbtec.email.business.application.service.email.EmailService;
import com.gbtec.email.business.utils.model.EmailEntityMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("integrationTest")
public class EmailTasksTest {
    @Autowired
    private EmailTasks tasks;

    @SpyBean
    private EmailService service;

    @Test
    public void markSpamTaskExecutionSameDay(){
        final CronTrigger trigger = new CronTrigger(EmailTasks.MARK_SPAM_TASK_CRON_CONFIG);
        final SimpleTriggerContext triggerContext = new SimpleTriggerContext();
        final Calendar testTime = Calendar.getInstance();
        testTime.set(Calendar.HOUR_OF_DAY, 9);
        triggerContext.update(null, null, testTime.toInstant());

        final Instant nextExecution = trigger.nextExecution(triggerContext);

        Assertions.assertNotNull(nextExecution);
        final LocalDateTime nextExecutionDateTime = LocalDateTime.ofInstant(nextExecution, ZoneId.systemDefault());
        Assertions.assertEquals(testTime.get(Calendar.DAY_OF_YEAR), nextExecutionDateTime.getDayOfYear());
        Assertions.assertEquals(10, nextExecutionDateTime.getHour());
    }

    @Test
    public void markSpamTaskExecutionNextDay(){
        final CronTrigger trigger = new CronTrigger(EmailTasks.MARK_SPAM_TASK_CRON_CONFIG);
        final SimpleTriggerContext triggerContext = new SimpleTriggerContext();
        final Calendar testTime = Calendar.getInstance();
        testTime.set(Calendar.HOUR_OF_DAY, 11);
        triggerContext.update(null, null, testTime.toInstant());

        final Instant nextExecution = trigger.nextExecution(triggerContext);

        Assertions.assertNotNull(nextExecution);
        final LocalDateTime nextExecutionDateTime = LocalDateTime.ofInstant(nextExecution, ZoneId.systemDefault());
        testTime.add(Calendar.DATE, 1);
        Assertions.assertEquals(testTime.get(Calendar.DAY_OF_YEAR), nextExecutionDateTime.getDayOfYear());
        Assertions.assertEquals(10, nextExecutionDateTime.getHour());
    }

    @Test
    public void executeMarkSpamEmailsWithoutEmails() {
        tasks.markSpamEmails();
        Mockito.verify(service, Mockito.never()).update(Mockito.anyList(), Mockito.anyBoolean());
    }

    @Test
    public void executeMarkSpamEmailsWithoutMatchingEmails() {
        final List<EmailEntity> emails = new ArrayList<>();
        emails.add(EmailEntityMother.draftFromAddress("other@gbtec.es"));
        emails.add(EmailEntityMother.draftFromAddress("test@gbtec.es"));
        emails.add(EmailEntityMother.draftFromAddress("a@gbtec.es"));
        service.create(emails);
        tasks.markSpamEmails();
        Mockito.verify(service, Mockito.never()).update(Mockito.anyList(), Mockito.anyBoolean());
        for(EmailEntity spamEmail : emails) {
            final Optional<EmailEntity> persistedEmail  = service.findByUuid(spamEmail.getUuid());
            Assertions.assertTrue(persistedEmail.isPresent());
            Assertions.assertEquals(EmailState.DRAFT, persistedEmail.get().getState());
        }
    }

    @Test
    public void executeMarkSpamEmailsWithMatchingEmails() {
        final List<EmailEntity> emails = new ArrayList<>();
        emails.add(EmailEntityMother.draftFromAddress(EmailTasks.EMAIL_ADDRESS_SPAM));
        emails.add(EmailEntityMother.draftFromAddress(EmailTasks.EMAIL_ADDRESS_SPAM));
        emails.add(EmailEntityMother.draftFromAddress(EmailTasks.EMAIL_ADDRESS_SPAM));
        service.create(emails);
        tasks.markSpamEmails();
        for(EmailEntity spamEmail : emails) {
            final Optional<EmailEntity> updatedEmail = service.findByUuid(spamEmail.getUuid());
            Assertions.assertTrue(updatedEmail.isPresent());
            Assertions.assertEquals(EmailState.SPAM, updatedEmail.get().getState());
        }
    }
}
