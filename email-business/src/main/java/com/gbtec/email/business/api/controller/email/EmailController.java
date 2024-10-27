package com.gbtec.email.business.api.controller.email;

import com.gbtec.email.business.api.model.EmailDTO;
import com.gbtec.email.business.api.model.conversors.ApiToBusinessConversor;
import com.gbtec.email.business.api.model.conversors.BusinessToApiConversor;
import com.gbtec.email.business.application.model.EmailEntity;
import com.gbtec.email.business.application.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("emailsapi")
public class EmailController {

    @Autowired
    private EmailService service;

    @GetMapping("/findById")
    public Optional<EmailDTO> findById(@RequestParam("id") Long id) {
        return service.findByUuid(id).map(BusinessToApiConversor::email);
    }

    @PutMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean create(@RequestBody EmailDTO email) {
        return service.create(ApiToBusinessConversor.email(email));
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean update(@RequestBody EmailDTO email) {
        final EmailEntity convertedEmail = ApiToBusinessConversor.email(email);
        return service.update(convertedEmail);
    }

    @DeleteMapping(value = "/delete")
    public boolean delete(@RequestParam Long id) {
        return service.delete(id);
    }
}
