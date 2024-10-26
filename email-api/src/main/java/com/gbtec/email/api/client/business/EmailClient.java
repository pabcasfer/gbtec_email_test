package com.gbtec.email.api.client.business;

import com.gbtec.email.api.client.business.model.EmailDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "emails", contextId = "emailClient", path = "/emailsapi")
@Qualifier("serviceInstanceListSupplier")
public interface EmailClient {
    @GetMapping("/findById")
    Optional<EmailDTO> findById(@RequestParam Long id);

    @PutMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean create(@RequestBody EmailDTO email);

    @DeleteMapping(value = "/delete")
    boolean delete(@RequestParam Long id);
}
