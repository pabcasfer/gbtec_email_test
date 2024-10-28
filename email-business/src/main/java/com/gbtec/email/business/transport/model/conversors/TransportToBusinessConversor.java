package com.gbtec.email.business.transport.model.conversors;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailReceiverEntity;
import com.gbtec.email.business.application.model.EmailState;
import com.gbtec.email.business.transport.model.EmailReceiverTransportDTO;
import com.gbtec.email.business.transport.model.EmailStateTransportDTO;
import com.gbtec.email.business.transport.model.EmailTransportDTO;

import java.util.List;
import java.util.stream.Collectors;

public class TransportToBusinessConversor {
    public static EmailEntity email(EmailTransportDTO email) {
        return EmailEntity.builder()
                .uuid(email.getUuid())
                .from(email.getFrom())
                .receivers(convertReceivers(email.getReceivers()))
                .body(email.getBody())
                .state(convertState(email.getState()))
                .creationTime(email.getCreationTime())
                .lastModifiedTime(email.getLastModifiedTime())
                .build();
    }

    private static List<EmailReceiverEntity> convertReceivers(List<EmailReceiverTransportDTO> receivers) {
        return receivers.stream().map(TransportToBusinessConversor::convertReceiver).collect(Collectors.toList());
    }

    private static EmailReceiverEntity convertReceiver(EmailReceiverTransportDTO receiver) {
        return EmailReceiverEntity.builder().emailTo(receiver.getEmailTo()).hidden(receiver.getHidden()).build();
    }

    private static EmailState convertState(EmailStateTransportDTO state) {
        return switch (state) {
            case SENDING -> EmailState.SENDING;
            case SENT ->  EmailState.SENT;
            case DRAFT -> EmailState.DRAFT;
            case DELETED -> EmailState.DELETED;
            case SPAM -> EmailState.SPAM;
        };
    }

    private TransportToBusinessConversor() {
    }
}
