package com.gbtec.email.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public final class EmailRequest {
    private Long emailId;
    private String emailFrom;
    private List<EmailAccountRequest> emailTo;
    private List<EmailAccountRequest> emailCC;
    private String emailBody;
    private int state;
}
