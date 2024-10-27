package com.gbtec.email.business.transport.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailTransportDTO {

    private Long uuid;

    private String from;

    private List<EmailReceiverTransportDTO> receivers;

    private Timestamp creationTime;

    private Timestamp lastModifiedTime;

    private EmailStateTransportDTO state;

    private String body;
}
