package com.gbtec.email.api.controller;

import com.gbtec.email.api.client.business.ApiToBusinessConversors;
import com.gbtec.email.api.client.business.EmailClient;
import com.gbtec.email.api.client.business.model.EmailDTO;
import com.gbtec.email.api.client.business.model.SuccessOrErrorResponse;
import com.gbtec.email.api.model.DeleteRequest;
import com.gbtec.email.api.model.EmailRequest;
import com.gbtec.email.api.model.FindByIdRequest;
import com.gbtec.email.api.model.FindRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    @PostMapping("/create")
    public ResponseEntity<SuccessOrErrorResponse> create(@RequestBody List<EmailRequest> emails) {
        final SuccessOrErrorResponse successOrErrorResponse = client.create(ApiToBusinessConversors.emails(emails));
        if(successOrErrorResponse.ok()) {
            return ResponseEntity.ok(successOrErrorResponse);
        } else {
            return ResponseEntity.internalServerError().body(successOrErrorResponse);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody List<EmailRequest> emails) {
        client.update(ApiToBusinessConversors.emails(emails));
        return ResponseEntity.ok("");
    }

    @DeleteMapping("/delete")
    public boolean delete(@RequestBody DeleteRequest request) {
        return client.delete(request.getId());
    }

    @GetMapping("/find")
    public List<EmailDTO> find(@RequestBody FindRequest request) {
        // TODO: Paginate results
        return client.find(ApiToBusinessConversors.findRequestToFilter(request));
    }
}
