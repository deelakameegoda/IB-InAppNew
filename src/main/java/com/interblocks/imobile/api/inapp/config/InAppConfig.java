package com.interblocks.imobile.api.inapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {
        "com.interblocks.iwallet.adaptor.rest.admin",
        "com.interblocks.iwallet.oauth",
        "com.interblocks.iwallet.config.core",
        "com.interblocks.iwallet.config.database",
        "com.interblocks.iwallet.config.hazelcast",
        "com.interblocks.iwallet.isodetails",
        "com.interblocks.iwallet.service",
        "com.interblocks.iwallet.api.qrcode",
        "com.interblocks.webtools",
        "com.interblocks.iwallet.subcomponents",
        "com.interblocks.iwallet.repository",
        "com.interblocks.iwallet.util",
        "com.interblocks.iwallet.adaptor",
        "com.interblocks.imobile.api.inapp"
})
@EnableJpaRepositories(basePackages = {
        "com.interblocks.iwallet.repository",
        "com.interblocks.webtools",
        "com.interblocks.iwallet.subcomponents"
})
public class InAppConfig {

}
