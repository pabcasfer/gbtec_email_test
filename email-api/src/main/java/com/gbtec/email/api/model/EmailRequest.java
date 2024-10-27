package com.gbtec.email.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public final class EmailRequest {
    private Long emailId;
    private String emailFrom;
    private List<EmailAccountRequest> emailTo;
    private List<EmailAccountRequest> emailCC;
    private String emailBody;
    private int state;
}
