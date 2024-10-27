package com.gbtec.email.business;

import com.gbtec.email.business.transport.comm.EmailPublishController;
import com.gbtec.email.business.transport.comm.RabbitConsumer;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local1")
public class RabbitMqTest {
    @Autowired
    private EmailPublishController emailPublishController;
    @Autowired
    private RabbitConsumer consumer;

    @Test
    public void test() {
        emailPublishController.sendEmail("{asdf:1}");
        Awaitility.await().until(() -> consumer.asdf);
    }
}
