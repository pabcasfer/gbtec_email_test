package com.gbtec.business.application.service;

import com.gbtec.business.application.model.EmailEntity;
import com.gbtec.business.application.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// FIXME: We should use custom exceptions
@Service
public class EmailApplicationService {

    @Autowired
    private EmailRepository repository;

    public String findById(Long id) {
        return String.valueOf(id);
    }

    public boolean create(EmailEntity email) {
        if(email.getUuid() == null) {
            throw new IllegalArgumentException("UUID must not be null");
        }
        if(!email.getState().isUpdatable()) {
            throw new IllegalArgumentException("Given state not allowed");
        }
        repository.save(email);
        return true;
    }
}
