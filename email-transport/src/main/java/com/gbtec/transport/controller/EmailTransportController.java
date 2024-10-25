package com.gbtec.transport.controller;

import com.gbtec.transport.client.business.EmailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("emails")
public class EmailTransportController {

    @Autowired
    private EmailClient client;

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public String findById(@RequestAttribute("id") Long id) {
        return client.findById(id);
    }
}
