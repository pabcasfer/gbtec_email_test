package com.gbtec.email.business.transport.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailReceiverTransportDTO {
    private String emailTo;

    private Boolean hidden;
}
