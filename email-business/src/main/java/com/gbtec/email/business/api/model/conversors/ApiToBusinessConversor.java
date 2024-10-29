package com.gbtec.email.business.api.model.conversors;

import com.gbtec.email.business.api.model.EmailDTO;
import com.gbtec.email.business.api.model.EmailFilterDTO;
import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.model.EmailEntityFilter;
import com.gbtec.email.business.application.model.EmailReceiverEntity;
import com.gbtec.email.business.application.model.EmailState;
import com.gbtec.utils.filters.FilterField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApiToBusinessConversor {

    public static EmailEntity email(EmailDTO email) {
        final List<EmailReceiverEntity> receivers = new ArrayList<>();
        email.getEmailTo().forEach(emailAddress -> receivers.add(EmailReceiverEntity.builder().emailTo(emailAddress).hidden(false).build()));
        email.getEmailCC().forEach(emailAddress -> receivers.add(EmailReceiverEntity.builder().emailTo(emailAddress).hidden(true).build()));
        return EmailEntity.builder()
                .uuid(email.getEmailId())
                .from(email.getEmailFrom())
                .body(email.getEmailBody())
                .state(emailState(email.getState()))
                .receivers(receivers)
                .build();
    }

    private static EmailState emailState(int state) {
        return switch(state) {
            case 1 -> EmailState.SENDING;
            case 2 -> EmailState.DRAFT;
            case 3 -> EmailState.DELETED;
            case 4 -> EmailState.SPAM;
            default -> throw new IllegalArgumentException("State not found");
        };
    }

    public static EmailEntityFilter emailFilter(EmailFilterDTO filter) {
        final EmailEntityFilter.EmailEntityFilterBuilder builder = EmailEntityFilter.builder();
        if (filter.getUuids() != null && !filter.getUuids().isEmpty()) {
            builder.uuids(FilterField.of(filter.getUuids()));
        }
        if (filter.getFroms() != null && !filter.getFroms().isEmpty()) {
            builder.froms(FilterField.of(filter.getFroms()));
        }
        if (filter.getTos() != null && !filter.getTos().isEmpty()) {
            builder.tos(FilterField.of(filter.getTos()));
        }
        if (filter.getCcs() != null && !filter.getCcs().isEmpty()) {
            builder.ccs(FilterField.of(filter.getCcs()));
        }
        if (filter.getStates() != null && !filter.getStates().isEmpty()) {
            builder.states(FilterField.of(emailStatesFilter(filter.getStates())));
        }
        if (filter.getBody() != null && !filter.getBody().isBlank()) {
            if(filter.isNullBody()) {
                throw new IllegalArgumentException("You cannot search for a null body and some text at the same time");
            }
            builder.body(FilterField.of(filter.getBody()));
        }
        if(filter.isNullBody()) {
            builder.body(FilterField.empty());
        }
        return builder.build();
    }

    private static List<EmailState> emailStatesFilter(List<Integer> states) {
        return states.stream().map(ApiToBusinessConversor::emailState).collect(Collectors.toList());
    }

    private ApiToBusinessConversor() {
    }
}
