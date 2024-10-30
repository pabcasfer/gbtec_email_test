package com.gbtec.email.business.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailExceptionDTO {
    private String type;
    private String message;
}
