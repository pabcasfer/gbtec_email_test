package com.gbtec.email.api.config.loadbalancing.emails;

import com.gbtec.email.api.config.loadbalancing.PropertiesServiceInstanceListSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class EmailClientConfiguration {

    @Autowired
    private EmailHostProperties hostProperties;

    @Bean
    @Primary
    ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new PropertiesServiceInstanceListSupplier(hostProperties.getConnectionInfos(), "emails");
    }
}
