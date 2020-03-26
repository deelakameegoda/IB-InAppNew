package com.interblocks.imobile.api.inapp.service.registration;

import com.interblocks.imobile.testconfig.SpringRuntime;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.testng.annotations.Test;

@Log4j2
@EnableAutoConfiguration
public class RegistrationImplTest extends SpringRuntime {
    private String testClass = this.getClass().getSimpleName();

    @Autowired
    RegistrationApiService registrationApiService;

    @Test
    public void testRegisterUser() {
        log.info("Start of " + testClass + "_testRegisterUser");
        log.info("End of " + testClass + "_testRegisterUser");
    }
}
