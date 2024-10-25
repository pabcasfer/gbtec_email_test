package com.gbtec.email.api.client.business.model;

import lombok.Builder;

import java.util.List;

@Builder
public record EmailDTO(Integer emailId, String emailFrom, List<String> emailTo, List<String> emailCC, String emailBody, int state) {
}
