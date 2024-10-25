package com.gbtec.transport.client.business;

import com.gbtec.transport.DemoServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class EmailConfiguration {

  @Bean
  @Primary
  ServiceInstanceListSupplier serviceInstanceListSupplier() {
    return new DemoServiceInstanceListSupplier("emails");
  }

}
