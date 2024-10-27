package com.gbtec.email.business.transport.model.conversors;

import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailReceiverEntity;
import com.gbtec.email.business.application.model.EmailState;
import com.gbtec.email.business.transport.model.EmailReceiverTransportDTO;
import com.gbtec.email.business.transport.model.EmailStateTransportDTO;
import com.gbtec.email.business.transport.model.EmailTransportDTO;

import java.util.List;
import java.util.stream.Collectors;

public class BusinessToTransportConversor {
    public static EmailTransportDTO email(EmailEntity email) {
        return EmailTransportDTO.builder()
                .uuid(email.getUuid())
                .from(email.getFrom())
                .receivers(convertReceivers(email.getReceivers()))
                .body(email.getBody())
                .state(convertState(email.getState()))
                .creationTime(email.getCreationTime())
                .lastModifiedTime(email.getLastModifiedTime())
                .build();
    }

    private static List<EmailReceiverTransportDTO> convertReceivers(List<EmailReceiverEntity> receivers) {
        return receivers.stream().map(BusinessToTransportConversor::convertReceiver).collect(Collectors.toList());
    }

    private static EmailReceiverTransportDTO convertReceiver(EmailReceiverEntity receiver) {
        return EmailReceiverTransportDTO.builder().emailTo(receiver.getEmailTo()).hidden(receiver.getHidden()).build();
    }

    private static EmailStateTransportDTO convertState(EmailState state) {
        return switch (state) {
            case SENDING -> EmailStateTransportDTO.SENDING;
            case SENT ->  EmailStateTransportDTO.SENT;
            case DRAFT -> EmailStateTransportDTO.DRAFT;
            case DELETED -> EmailStateTransportDTO.DELETED;
            case SPAM -> EmailStateTransportDTO.SPAM;
        };
    }

    private BusinessToTransportConversor() {
    }
}
