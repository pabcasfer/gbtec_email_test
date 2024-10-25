package com.gbtec.transport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailPublishController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    @Retryable(retryFor = {AmqpException.class}, maxAttemptsExpression = "#{${server.retry.policy.max.attempts:3}}",
            backoff = @Backoff(delayExpression = "#{${server.retry.policy.delay:36000}}",
                    multiplierExpression = "#{${server.retry.policy.multiplier:2}}",
                    maxDelayExpression = "#{${server.retry.policy.max.delay:252000}}"))
    public void sendEmail(String emailJson) throws AmqpException {
        // Probably we need to generate this with the id info of the email
        String correlationId = generateUniqueCorrelationId();
        log.debug("Publishing email message with id {} and content {}", correlationId, emailJson);
        rabbitTemplate.convertAndSend(RabbitConfiguration.QUEUE_NAME, emailJson, m -> {
            m.getMessageProperties().setCorrelationId(correlationId);
            return m;
        });
    }

    private String generateUniqueCorrelationId() {
        return "";
    }
}