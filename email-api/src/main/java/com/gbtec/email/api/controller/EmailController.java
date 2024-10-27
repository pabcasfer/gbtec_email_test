package com.gbtec.email.api.controller;

import com.gbtec.email.api.client.business.ApiToBusinessConversors;
import com.gbtec.email.api.client.business.EmailClient;
import com.gbtec.email.api.client.business.model.EmailDTO;
import com.gbtec.email.api.model.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailClient client;

    @GetMapping("/findById")
    public Optional<EmailDTO> findById(@RequestParam("id") Long id) {
        return client.findById(id);
    }

    @PutMapping("/create")
    public boolean create(@RequestBody EmailRequest email) {
        // FIXME: Use a @ControllerAdvice class and here only return ResponseEntity<Success>
        return client.create(ApiToBusinessConversors.email(email));
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestParam("id") Long id) {
        return client.delete(id);
    }
}
