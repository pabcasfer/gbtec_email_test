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
public final class Email {
    private Integer emailId;
    private String emailFrom;
    private List<EmailAccount> emailTo;
    private List<EmailAccount> emailCC;
    private String emailBody;
    private int state;
}
