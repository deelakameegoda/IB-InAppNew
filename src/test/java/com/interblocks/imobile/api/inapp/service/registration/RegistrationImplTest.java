package com.interblocks.imobile.api.inapp.service.registration;

import com.interblocks.imobile.api.inapp.model.RegisterUserRequest;
import com.interblocks.imobile.testconfig.SpringRuntime;
import com.interblocks.imobile.testmocks.restmodels.RegisterUserRequestMock;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

@Log4j2
@EnableAutoConfiguration
public class RegistrationImplTest extends SpringRuntime {
    private String testClass = this.getClass().getSimpleName();

    @Autowired
    RegistrationApiService registrationApiService;

    RegisterUserRequest registerUserRequest;

    @Test
    public void testRegisterUser() {
        log.info("Start of " + testClass + "_testRegisterUser");

        registerUserRequest = RegisterUserRequestMock.getInstance().createMockRegisterUserRequest("userId");

        Assert.assertEquals(registrationApiService.registerUser(registerUserRequest).getStatusCode().toString(), "200");
        log.info("End of " + testClass + "_testRegisterUser");
    }

    @Test
    public void testRegisterUser_FailResponse() {
        log.info("Start of " + testClass + "_testRegisterUser_FailResponse");

        registerUserRequest = RegisterUserRequestMock.getInstance().createMockRegisterUserRequest("userIdX");

        Assert.assertEquals(registrationApiService.registerUser(registerUserRequest).getStatusCode().toString(), "401");
        log.info("End of " + testClass + "_testRegisterUser_FailResponse");
    }
}
