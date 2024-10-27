package com.gbtec.email.business.application.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("local1")
public class EmailTasksTest {
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
}
