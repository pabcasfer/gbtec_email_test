package com.gbtec.business.application.service;

import com.gbtec.business.application.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailApplicationService {

    @Autowired
    private EmailRepository repository;

    public String findById(Long id) {
        return String.valueOf(id);
    }
}
