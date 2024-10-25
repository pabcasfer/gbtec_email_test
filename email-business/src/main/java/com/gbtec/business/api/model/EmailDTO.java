package com.gbtec.business.api.model;

import lombok.Builder;

import java.util.List;
import java.util.Objects;

@Builder
public final class EmailDTO {
    private Integer emailId;
    private String emailFrom;
    private List<String> emailTo;
    private List<String> emailCC;
    private String emailBody;
    private int state;

    public EmailDTO(Integer emailId, String emailFrom, List<String> emailTo, List<String> emailCC, String emailBody, int state) {
        this.emailId = emailId;
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.emailCC = emailCC;
        this.emailBody = emailBody;
        this.state = state;
    }

    public Integer emailId() {
        return emailId;
    }

    public String emailFrom() {
        return emailFrom;
    }

    public List<String> emailTo() {
        return emailTo;
    }

    public List<String> emailCC() {
        return emailCC;
    }

    public String emailBody() {
        return emailBody;
    }

    public int state() {
        return state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (EmailDTO) obj;
        return Objects.equals(this.emailId, that.emailId) &&
                Objects.equals(this.emailFrom, that.emailFrom) &&
                Objects.equals(this.emailTo, that.emailTo) &&
                Objects.equals(this.emailCC, that.emailCC) &&
                Objects.equals(this.emailBody, that.emailBody) &&
                this.state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailId, emailFrom, emailTo, emailCC, emailBody, state);
    }

    @Override
    public String toString() {
        return "EmailDTO[" +
                "emailId=" + emailId + ", " +
                "emailFrom=" + emailFrom + ", " +
                "emailTo=" + emailTo + ", " +
                "emailCC=" + emailCC + ", " +
                "emailBody=" + emailBody + ", " +
                "state=" + state + ']';
    }

}
