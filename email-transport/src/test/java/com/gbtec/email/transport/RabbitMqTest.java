package com.gbtec.email.transport;

import com.gbtec.transport.EmailPublishController;
import com.gbtec.transport.RabbitConsumer;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("local")
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
