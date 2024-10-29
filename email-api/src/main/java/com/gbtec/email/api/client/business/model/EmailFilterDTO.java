package com.gbtec.email.api.client.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailFilterDTO {
    private List<Long> uuids;

    private List<String> froms;

    private List<String> tos;

    private List<String> ccs;

    private List<Integer> states;

    private String body;

    private boolean nullBody;
}

