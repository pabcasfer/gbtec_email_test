package com.gbtec.business.api.controller;

import com.gbtec.business.api.model.EmailDTO;
import com.gbtec.business.api.model.conversors.ApiToBusinessConversor;
import com.gbtec.business.api.model.conversors.BusinessToApiConversor;
import com.gbtec.business.application.service.EmailApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("emailsapi")
public class EmailController {

    @Autowired
    private EmailApplicationService service;

    @GetMapping("/findById")
    public Optional<EmailDTO> findById(@RequestParam("id") Long id) {
        return service.findByUuid(id).map(BusinessToApiConversor::email);
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean create(@RequestBody EmailDTO email) {
        return service.create(ApiToBusinessConversor.email(email));
    }

    @DeleteMapping(value = "/delete")
    public boolean delete(@RequestParam Long id) {
        return service.delete(id);
    }
}
