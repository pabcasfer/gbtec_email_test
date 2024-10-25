package com.gbtec.email.api.config.loadbalancing.emails;

import com.gbtec.email.api.config.loadbalancing.HostConnectionInfo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("loadbalancing.emails")
@Data
public class EmailHostProperties {

    @NestedConfigurationProperty
    private List<HostConnectionInfo> connectionInfos;

}