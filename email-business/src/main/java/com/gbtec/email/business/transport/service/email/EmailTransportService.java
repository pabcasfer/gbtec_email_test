package com.gbtec.email.business.transport.service.email;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.service.email.EmailService;
import com.gbtec.email.business.transport.comm.EmailPublishController;
import com.gbtec.email.business.transport.model.EmailTransportDTO;
import com.gbtec.email.business.transport.model.conversors.BusinessToTransportConversor;
import com.gbtec.email.business.transport.model.conversors.TransportToBusinessConversor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailTransportService {

    @Autowired
    @Lazy
    private EmailService service;

    @Autowired
    private EmailPublishController publisher;

    public void send(EmailEntity email) {
        publisher.publish(BusinessToTransportConversor.email(email));
    }

    public void receive(EmailTransportDTO email) {
        this.service.processReceived(TransportToBusinessConversor.email(email));
    }
}
