package com.gbtec.email.business.transport.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailTransportDTO {

    private Long uuid;

    private String from;

    private List<EmailReceiverTransportDTO> receivers;

    private Instant creationTime;

    private Instant lastModifiedTime;

    private EmailStateTransportDTO state;

    private String body;
}
