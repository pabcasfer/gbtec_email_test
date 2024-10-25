package com.gbtec.business.api.controller;

import com.gbtec.business.api.model.EmailDTO;
import com.gbtec.business.api.model.conversors.ApiToBusinessConversor;
import com.gbtec.business.application.service.EmailApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("emailsapi")
public class EmailController {

    @Autowired
    private EmailApplicationService service;

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public String findById(@RequestAttribute("id") Long id) {
        return service.findById(id);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean create(@RequestBody EmailDTO email) {
        return service.create(ApiToBusinessConversor.email(email));
    }
}
