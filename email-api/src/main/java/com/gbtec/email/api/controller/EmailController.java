package com.gbtec.email.api.controller;

import com.gbtec.email.api.client.business.ApiToBusinessConversors;
import com.gbtec.email.api.client.business.EmailClient;
import com.gbtec.email.api.client.business.model.EmailDTO;
import com.gbtec.email.api.model.DeleteRequest;
import com.gbtec.email.api.model.EmailRequest;
import com.gbtec.email.api.model.FindByIdRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailClient client;

    @GetMapping("/findById")
    public Optional<EmailDTO> findById(@RequestBody FindByIdRequest request) {
        return client.findById(request.getId());
    }

    @PutMapping("/create")
    public boolean create(@RequestBody EmailRequest email) {
        // FIXME: Use a @ControllerAdvice class and here only return ResponseEntity<Success>
        return client.create(ApiToBusinessConversors.email(email));
    }

    @PostMapping("/update")
    public boolean update(@RequestBody EmailRequest email) {
        // FIXME: Use a @ControllerAdvice class and here only return ResponseEntity<Success>
        return client.update(ApiToBusinessConversors.email(email));
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestBody DeleteRequest request) {
        return client.delete(request.getId());
    }
}
