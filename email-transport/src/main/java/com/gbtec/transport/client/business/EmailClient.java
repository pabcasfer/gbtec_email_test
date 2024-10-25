package com.gbtec.transport.client.business;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "emails", contextId = "emailClient", path = "/emails", url = "${emails-business.url}", configuration = EmailConfiguration.class)
public interface EmailClient {
    @GetMapping("/findById")
    String findById(@RequestBody Long id);
}
