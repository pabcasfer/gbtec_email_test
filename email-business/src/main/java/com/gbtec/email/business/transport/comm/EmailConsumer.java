package com.gbtec.email.business.transport.comm;

import com.gbtec.email.business.transport.comm.config.EmailRabbitConfiguration;
import com.gbtec.email.business.transport.model.EmailTransportDTO;
import com.gbtec.email.business.transport.service.email.EmailTransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailConsumer {

    @Autowired
    private EmailTransportService service;

    @RabbitListener(containerFactory = "customRabbitListenerContainerFactory",
            bindings = @QueueBinding(value = @Queue,
                    exchange = @Exchange(name = EmailRabbitConfiguration.EXCHANGE_NAME, type = ExchangeTypes.FANOUT)))
    @RabbitHandler
    public void onMessageFromRabbitMQ(@Payload EmailTransportDTO email) {
        try {
            service.receive(email);
        } catch (Exception e) {
            log.error("Error processing email transport", e);
            // TODO: Send notification to business microservice so they can know the new state of the email
        }
    }
}
