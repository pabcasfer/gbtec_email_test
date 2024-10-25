package com.gbtec.transport.service;

import org.springframework.stereotype.Service;

@Service
public class EmailTransportService {

    public String findById(Long id) {
        return String.valueOf(id);
    }
}
