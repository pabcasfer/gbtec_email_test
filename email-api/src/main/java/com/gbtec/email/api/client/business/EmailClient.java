package com.gbtec.email.api.client.business;

import com.gbtec.email.api.client.business.model.EmailDTO;
import com.gbtec.email.api.client.business.model.EmailFilterDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "emails", contextId = "emailClient", path = "/emailsapi")
@Qualifier("serviceInstanceListSupplier")
public interface EmailClient {
    @GetMapping("/findById")
    Optional<EmailDTO> findById(@RequestParam Long id);

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean create(@RequestBody List<EmailDTO> emails);

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    boolean update(@RequestBody List<EmailDTO> emails);

    @DeleteMapping(value = "/delete")
    boolean delete(@RequestParam Long id);

    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<EmailDTO> find(@SpringQueryMap EmailFilterDTO filter);
}
