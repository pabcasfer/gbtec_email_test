package com.gbtec.business.transport;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "emailTransport", contextId = "emailTransportClient", path = "/emails", url = "${email-transport.url}")
public interface EmailTransportClient {
    @GetMapping("/findById")
    String findById(@RequestBody Long id);
}
