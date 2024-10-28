package com.gbtec.email.business.integration;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailState;
import com.gbtec.email.business.application.service.email.EmailService;
import com.gbtec.email.business.transport.comm.EmailPublishController;
import com.gbtec.email.business.transport.model.EmailTransportDTO;
import com.gbtec.email.business.utils.model.EmailTransportDTOMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("local1")
public class EmailTransportIntegrationTest {
    @Autowired
    private EmailPublishController emailPublishController;

    @MockBean
    private EmailService service;

    @Test
    public void publishAndReceiveEmail() {
        final EmailTransportDTO emailToSend = EmailTransportDTOMother.sending();
        emailPublishController.publish(emailToSend);
        final ArgumentCaptor<EmailEntity> emailEntityArgumentCaptor = ArgumentCaptor.captor();
        Mockito.verify(service, Mockito.timeout(500L)).processReceived(emailEntityArgumentCaptor.capture());
        final EmailEntity receivedEmail = emailEntityArgumentCaptor.getValue();
        Assertions.assertNotNull(receivedEmail);
        Assertions.assertEquals(emailToSend.getUuid(), receivedEmail.getUuid());
        Assertions.assertEquals(emailToSend.getFrom(), receivedEmail.getFrom());
        Assertions.assertEquals(emailToSend.getBody(), receivedEmail.getBody());
        Assertions.assertEquals(emailToSend.getCreationTime(), receivedEmail.getCreationTime());
        Assertions.assertEquals(emailToSend.getLastModifiedTime(), receivedEmail.getLastModifiedTime());
        Assertions.assertEquals(EmailState.SENDING, receivedEmail.getState());
        Assertions.assertEquals(emailToSend.getReceivers().size(), receivedEmail.getReceivers().size());
        for(int i = 0; i < receivedEmail.getReceivers().size(); i++) {
            Assertions.assertEquals(emailToSend.getReceivers().get(i).getEmailTo(), receivedEmail.getReceivers().get(i).getEmailTo());
            Assertions.assertEquals(emailToSend.getReceivers().get(i).getHidden(), receivedEmail.getReceivers().get(i).getHidden());
        }
    }
}
