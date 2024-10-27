package com.gbtec.business.application.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum EmailState {
    DRAFT(1), SENDING(2), SENT(3), DELETED(4), SPAM(5);

    private final int id;

    EmailState(int id) {
        this.id = id;
    }

    public boolean isUpdatable() {
        return DRAFT.equals(this);
    }

    public static Optional<EmailState> findById(int id) {
        return Arrays.stream(values()).filter(state -> state.getId() == id).findFirst();
    }
}
