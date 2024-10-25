package com.gbtec.email.api.config.loadbalancing;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class PropertiesServiceInstanceListSupplier implements ServiceInstanceListSupplier {

  private final List<HostConnectionInfo> properties;
  private final String serviceId;

  public PropertiesServiceInstanceListSupplier(List<HostConnectionInfo> properties, String serviceId) {
    this.properties = properties;
    this.serviceId = serviceId;
  }

  @Override
  public String getServiceId() {
    return serviceId;
  }

  @Override
  public Flux<List<ServiceInstance>> get() {
    final List<ServiceInstance> serviceInstances = new ArrayList<>();
    for(int i = 0; i < properties.size(); i++) {
      final HostConnectionInfo property = properties.get(i);
      serviceInstances.add(new DefaultServiceInstance(serviceId + i, serviceId, property.host(), property.port(), false));
    }

    return Flux.just(serviceInstances);
  }
}
