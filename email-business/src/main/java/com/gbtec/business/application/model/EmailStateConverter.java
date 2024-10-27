package com.gbtec.business.application.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EmailStateConverter implements AttributeConverter<EmailState, Integer> {
 
    @Override
    public Integer convertToDatabaseColumn(EmailState state) {
        return state.getId();
    }

    @Override
    public EmailState convertToEntityAttribute(Integer id) {

        return EmailState.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}