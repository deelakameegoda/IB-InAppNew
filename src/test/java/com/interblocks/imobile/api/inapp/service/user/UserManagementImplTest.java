package com.interblocks.imobile.api.inapp.service.user;

import com.interblocks.imobile.api.inapp.model.CustomerProfileEditRequest;
import com.interblocks.imobile.api.inapp.model.ExternalUserLogin;
import com.interblocks.imobile.api.inapp.model.ListWalletRequest;
import com.interblocks.imobile.testconfig.SpringRuntime;
import com.interblocks.imobile.testmocks.restmodels.CustomerProfileEditRequestMock;
import com.interblocks.imobile.testmocks.restmodels.ExternalUserLoginMock;
import com.interblocks.imobile.testmocks.restmodels.ListWalletRequestMock;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

@Log4j2
@EnableAutoConfiguration
public class UserManagementImplTest extends SpringRuntime {

    private String testClass = this.getClass().getSimpleName();

    @Autowired
    UserManagementApiService userManagementApiService;

    ExternalUserLogin externalUserLogin;
    CustomerProfileEditRequest customerProfileEditRequest;
    ListWalletRequest listWalletRequest;

    @Test
    public void testLogin() {
        log.info("Start of " + testClass + "_testLogin");

        externalUserLogin = ExternalUserLoginMock.getInstance().createMockExternalUserLogin("000");

        Assert.assertEquals(userManagementApiService.login(externalUserLogin).getStatusCode().toString(), "200");

        log.info("End of " + testClass + "_testLogin");
    }

    @Test
    public void testLogin_FailResponse() {
        log.info("Start of " + testClass + "_testLogin_FailResponse");

        externalUserLogin = ExternalUserLoginMock.getInstance().createMockExternalUserLogin("123");

        Assert.assertEquals(userManagementApiService.login(externalUserLogin).getStatusCode().toString(), "401");

        log.info("End of " + testClass + "_testLogin_FailResponse");
    }

    @Test
    public void testEdit() {
        log.info("Start of " + testClass + "_testEdit");

        customerProfileEditRequest = CustomerProfileEditRequestMock.getInstance().createMockCustomerProfileEditRequest("000");

        Assert.assertEquals(userManagementApiService.edit(customerProfileEditRequest).getStatusCode().toString(), "200");

        log.info("End of " + testClass + "_testEdit");
    }

    @Test
    public void testEdit_FailResponse() {
        log.info("Start of " + testClass + "_testEdit_FailResponse");

        customerProfileEditRequest = CustomerProfileEditRequestMock.getInstance().createMockCustomerProfileEditRequest("123");

        Assert.assertEquals(userManagementApiService.edit(customerProfileEditRequest).getStatusCode().toString(), "401");

        log.info("End of " + testClass + "_testEdit_FailResponse");
    }

    @Test
    public void testList() {
        log.info("Start of " + testClass + "_testList");

        listWalletRequest = ListWalletRequestMock.getInstance().createMockListWalletRequest("000");

        Assert.assertEquals(userManagementApiService.list(listWalletRequest).getStatusCode().toString(), "200");

        log.info("End of " + testClass + "_testList");
    }

    @Test
    public void testList_FailResponse() {
        log.info("Start of " + testClass + "_testList_FailResponse");

        listWalletRequest = ListWalletRequestMock.getInstance().createMockListWalletRequest("123");

        Assert.assertEquals(userManagementApiService.list(listWalletRequest).getStatusCode().toString(), "401");

        log.info("End of " + testClass + "_testList_FailResponse");
    }
}
