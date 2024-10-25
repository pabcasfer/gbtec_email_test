package com.gbtec.business.application.model;

public enum EmailState {
    DRAFT, SENDING, SENT, DELETED, SPAM;

    public boolean isUpdatable() {
        return DRAFT.equals(this);
    }
}
