package com.gbtec.email.business.transport.comm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitConsumer {

    public boolean asdf = false;

    @RabbitListener(queues = RabbitConfiguration.QUEUE_NAME, containerFactory = "customRabbitListenerContainerFactory")
    @RabbitHandler
    public void onMessageFromRabbitMQ(@Payload String payload, Message<String> message) {
        try {
            final String messageId = (String) message.getHeaders().get(AmqpHeaders.MESSAGE_ID);
            final String correlationId = (String) message.getHeaders().get(AmqpHeaders.CORRELATION_ID);
            log.debug("rabbitMQ message id: {}", messageId);
            log.debug("correlation id : {}", correlationId);
            log.debug("email payload {}", payload);
            asdf = true;
            //emailTransportService.processEmail(payload);
        } catch (Exception e) {
            log.error("Error processing email transport", e);
            // TODO: Send notification to business microservice so they can know the new state of the email
        }
    }
}
