package com.interblocks.imobile.api.inapp.service.card;

import com.interblocks.imobile.api.inapp.model.AddCardsRequest;
import com.interblocks.imobile.api.inapp.model.ListExternalCardRequest;
import com.interblocks.imobile.testconfig.SpringRuntime;
import com.interblocks.imobile.testmocks.restmodels.AddCardsRequestMock;
import com.interblocks.imobile.testmocks.restmodels.ListExternalCardRequestMock;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

@Log4j2
@EnableAutoConfiguration
public class CardManagementImplTest extends SpringRuntime {

    private String testClass = this.getClass().getSimpleName();

    @Autowired
    CardManagementApiService cardManagementApiService;

    AddCardsRequest addCardsRequest;
    ListExternalCardRequest listExternalCardRequest;

    @Test
    public void testAdd() {
        log.info("Start of " + testClass + "_testAdd");

        addCardsRequest = AddCardsRequestMock.getInstance().createMockAddCardsRequest("123", "456");

        Assert.assertEquals(cardManagementApiService.add(addCardsRequest).getStatusCode().toString(),"200");

        log.info("End of " + testClass + "_testAdd");
    }

    @Test
    public void testAdd_FailResponse() {
        log.info("Start of " + testClass + "_testAdd_FailResponse");

        addCardsRequest = AddCardsRequestMock.getInstance().createMockAddCardsRequest("000", "456");

        Assert.assertEquals(cardManagementApiService.add(addCardsRequest).getStatusCode().toString(),"401");

        log.info("End of " + testClass + "_testAdd_FailResponse");
    }

    @Test
    public void testList() {
        log.info("Start of " + testClass + "_testList");

        listExternalCardRequest = ListExternalCardRequestMock.getInstance().createMockListExternalCardRequest("123", "456");

        Assert.assertEquals(cardManagementApiService.list(listExternalCardRequest).getStatusCode().toString(),"200");

        log.info("End of " + testClass + "_testList");
    }

    @Test
    public void testList_FailResponse() {
        log.info("Start of " + testClass + "_testList_FailResponse");

        listExternalCardRequest = ListExternalCardRequestMock.getInstance().createMockListExternalCardRequest("000", "456");

        Assert.assertEquals(cardManagementApiService.list(listExternalCardRequest).getStatusCode().toString(),"401");

        log.info("End of " + testClass + "_testList_FailResponse");
    }

    @Test
    public void testListAll() {
        log.info("Start of " + testClass + "_testListAll");

        listExternalCardRequest = ListExternalCardRequestMock.getInstance().createMockListExternalCardRequest("123", "456");

        Assert.assertEquals(cardManagementApiService.listAll(listExternalCardRequest).getStatusCode().toString(),"200");

        log.info("End of " + testClass + "_testListAll");
    }

    @Test
    public void testListAll_FailResponse() {
        log.info("Start of " + testClass + "_testListAll_FailResponse");

        listExternalCardRequest = ListExternalCardRequestMock.getInstance().createMockListExternalCardRequest("000", "456");

        Assert.assertEquals(cardManagementApiService.listAll(listExternalCardRequest).getStatusCode().toString(),"401");

        log.info("End of " + testClass + "_testListAll_FailResponse");
    }
}
