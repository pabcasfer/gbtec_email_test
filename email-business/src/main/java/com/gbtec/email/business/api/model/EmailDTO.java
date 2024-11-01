package com.gbtec.email.business.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public final class EmailDTO {
    private Long emailId;
    private String emailFrom;
    private List<String> emailTo;
    private List<String> emailCC;
    private String emailBody;
    private int state;
}
