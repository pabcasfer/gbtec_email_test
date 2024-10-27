package com.gbtec.email.business.transport.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum EmailStateTransportDTO {
    DRAFT(1), SENDING(2), SENT(3), DELETED(4), SPAM(5);

    private final int id;

    EmailStateTransportDTO(int id) {
        this.id = id;
    }

    public boolean isUpdatable() {
        return DRAFT.equals(this);
    }

    public static Optional<EmailStateTransportDTO> findById(int id) {
        return Arrays.stream(values()).filter(state -> state.getId() == id).findFirst();
    }

    public static boolean isTransport(EmailStateTransportDTO state) {
        return SENDING.equals(state) || SENT.equals(state);
    }
}
