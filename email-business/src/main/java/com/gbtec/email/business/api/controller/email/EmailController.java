package com.gbtec.email.business.api.controller.email;

import com.gbtec.email.business.api.model.EmailDTO;
import com.gbtec.email.business.api.model.EmailFilterDTO;
import com.gbtec.email.business.api.model.conversors.ApiToBusinessConversor;
import com.gbtec.email.business.api.model.conversors.BusinessToApiConversor;
import com.gbtec.email.business.application.model.EmailEntityFilter;
import com.gbtec.email.business.application.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean create(@RequestBody List<EmailDTO> emails) {
        return service.create(ApiToBusinessConversor.emails(emails));
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean update(@RequestBody List<EmailDTO> emails) {
        return service.update(ApiToBusinessConversor.emails(emails), true);
    }

    @DeleteMapping(value = "/delete")
    public boolean delete(@RequestParam Long id) {
        return service.delete(id);
    }

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<EmailDTO> find(@SpringQueryMap EmailFilterDTO filter) {
        final EmailEntityFilter convertedFilter = ApiToBusinessConversor.emailFilter(filter);
        return BusinessToApiConversor.emails(service.find(convertedFilter));
    }
}
