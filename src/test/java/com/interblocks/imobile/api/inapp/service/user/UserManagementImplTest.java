package com.interblocks.imobile.api.inapp.service.user;

import com.interblocks.imobile.testconfig.SpringRuntime;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.testng.annotations.Test;

@Log4j2
@EnableAutoConfiguration
public class UserManagementImplTest extends SpringRuntime {

    private String testClass = this.getClass().getSimpleName();

    @Autowired
    UserManagementApiService userManagementApiService;

    @Test
    public void testLogin() {
        log.info("Start of " + testClass + "_testLogin");
        log.info("End of " + testClass + "_testLogin");
    }
}
