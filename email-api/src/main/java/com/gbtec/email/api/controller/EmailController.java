package com.gbtec.email.api.controller;

import com.gbtec.email.api.client.business.ApiToBusinessConversors;
import com.gbtec.email.api.client.business.EmailClient;
import com.gbtec.email.api.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailClient client;

    @GetMapping("/findById")
    public String findById(@RequestParam("id") Long id) {
        return client.findById(id);
    }

    @PostMapping("/create")
    public boolean create(@RequestBody Email email) {
        // FIXME: Use a @ControllerAdvice class and here only return ResponseEntity<Success>
        return client.create(ApiToBusinessConversors.email(email));
    }

    // Fill with CRUD operations
}
