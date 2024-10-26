package com.gbtec.business.application.model;

import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

public enum EmailState {
    DRAFT(1), SENDING(2), SENT(3), DELETED(999), SPAM(5);

    @Id
    @Getter
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
