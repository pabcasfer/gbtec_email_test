package com.gbtec.email.business.transport.service.email;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.transport.model.EmailTransportDTO;
import com.gbtec.email.business.transport.model.conversors.BusinessToTransportConversor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailTransportService {

    public void send(EmailEntity email) {
        final EmailTransportDTO emailToSend = BusinessToTransportConversor.email(email);
        log.info("Sending email {}", emailToSend);
        // TODO: Send message over rabbitmq
    }
}
