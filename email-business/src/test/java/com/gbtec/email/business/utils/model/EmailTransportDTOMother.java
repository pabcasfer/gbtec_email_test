package com.gbtec.email.business.utils.model;

import com.gbtec.email.business.transport.model.EmailReceiverTransportDTO;
import com.gbtec.email.business.transport.model.EmailStateTransportDTO;
import com.gbtec.email.business.transport.model.EmailTransportDTO;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class EmailTransportDTOMother {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static EmailTransportDTO sending() {
        return EmailTransportDTO.builder()
                .uuid(RANDOM.nextLong(1, 999999))
                .from("a@gbtec.es")
                .state(EmailStateTransportDTO.SENDING)
                .body("body")
                .creationTime(Instant.now())
                .lastModifiedTime(Instant.now())
                .receivers(receivers())
                .build();
    }

    private static List<EmailReceiverTransportDTO> receivers() {
        final List<EmailReceiverTransportDTO> receivers = new ArrayList<>();
        receivers.add(EmailReceiverTransportDTO.builder().emailTo("b@gbtec.es").hidden(false).build());
        receivers.add(EmailReceiverTransportDTO.builder().emailTo("c@gbtec.es").hidden(true).build());
        return receivers;
    }

    private EmailTransportDTOMother() {
    }
}
