package com.interblocks.imobile.api.inapp.service.card;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.imobile.testconfig.SpringRuntime;
import com.interblocks.imobile.testmocks.restmodels.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
    EditExternalCardsRequest editExternalCardsRequest;
    DeleteExternalCardRequest deleteExternalCardRequest;
    ActivateExternalCardRequest activateExternalCardRequest;
    CheckCardInUseRequest checkCardInUseRequest;

    @Test
    public void testAdd() {
        log.info("Start of " + testClass + "_testAdd");

        addCardsRequest = AddCardsRequestMock.getInstance().createMockAddCardsRequest("123", "456");

        Assert.assertEquals(cardManagementApiService.add(addCardsRequest).getStatusCode().toString(), "200");

        log.info("End of " + testClass + "_testAdd");
    }

    @Test
    public void testAdd_FailResponse() {
        log.info("Start of " + testClass + "_testAdd_FailResponse");

        addCardsRequest = AddCardsRequestMock.getInstance().createMockAddCardsRequest("000", "456");

        Assert.assertEquals(cardManagementApiService.add(addCardsRequest).getStatusCode().toString(), "401");

        log.info("End of " + testClass + "_testAdd_FailResponse");
    }

    @Test
    public void testList() {
        log.info("Start of " + testClass + "_testList");

        listExternalCardRequest = ListExternalCardRequestMock.getInstance().createMockListExternalCardRequest("123", "456");

        Assert.assertEquals(cardManagementApiService.list(listExternalCardRequest).getStatusCode().toString(), "200");

        log.info("End of " + testClass + "_testList");
    }

    @Test
    public void testList_FailResponse() {
        log.info("Start of " + testClass + "_testList_FailResponse");

        listExternalCardRequest = ListExternalCardRequestMock.getInstance().createMockListExternalCardRequest("000", "456");

        Assert.assertEquals(cardManagementApiService.list(listExternalCardRequest).getStatusCode().toString(), "401");

        log.info("End of " + testClass + "_testList_FailResponse");
    }

    @Test
    public void testListAll() {
        log.info("Start of " + testClass + "_testListAll");

        listExternalCardRequest = ListExternalCardRequestMock.getInstance().createMockListExternalCardRequest("123", "456");

        Assert.assertEquals(cardManagementApiService.listAll(listExternalCardRequest).getStatusCode().toString(), "200");

        log.info("End of " + testClass + "_testListAll");
    }

    @Test
    public void testListAll_FailResponse() {
        log.info("Start of " + testClass + "_testListAll_FailResponse");

        listExternalCardRequest = ListExternalCardRequestMock.getInstance().createMockListExternalCardRequest("000", "456");

        Assert.assertEquals(cardManagementApiService.listAll(listExternalCardRequest).getStatusCode().toString(), "401");

        log.info("End of " + testClass + "_testListAll_FailResponse");
    }

    @Test
    public void testEdit() {
        log.info("Start of " + testClass + "_testEdit");

        editExternalCardsRequest = EditExternalCardsRequestMock.getInstance().createMockEditExternalCardsRequest("123", "456");

        Assert.assertEquals(cardManagementApiService.edit(editExternalCardsRequest).getStatusCode().toString(), "200");

        log.info("End of " + testClass + "_testEdit");
    }

    @Test
    public void testEdit_FailResponse() {
        log.info("Start of " + testClass + "_testEdit_FailResponse");

        editExternalCardsRequest = EditExternalCardsRequestMock.getInstance().createMockEditExternalCardsRequest("000", "456");

        Assert.assertEquals(cardManagementApiService.edit(editExternalCardsRequest).getStatusCode().toString(), "401");

        log.info("End of " + testClass + "_testEdit_FailResponse");
    }

    @Test
    public void testDelete() {
        log.info("Start of " + testClass + "_testDelete");

        deleteExternalCardRequest = DeleteExternalCardRequestMock.getInstance().createMockDeleteExternalCardRequest("123", "456");

        Assert.assertEquals(cardManagementApiService.delete(deleteExternalCardRequest).getStatusCode().toString(), "200");

        log.info("End of " + testClass + "_testDelete");
    }

    @Test
    public void testDelete_FailResponse() {
        log.info("Start of " + testClass + "_testDelete_FailResponse");

        deleteExternalCardRequest = DeleteExternalCardRequestMock.getInstance().createMockDeleteExternalCardRequest("000", "456");

        Assert.assertEquals(cardManagementApiService.delete(deleteExternalCardRequest).getStatusCode().toString(), "401");

        log.info("End of " + testClass + "_testDelete_FailResponse");
    }

    @Test
    public void testActivate() {
        log.info("Start of " + testClass + "_testActivate");

        activateExternalCardRequest = ActivateExternalCardRequestMock.getInstance().createMockActivateExternalCardRequest("123", "456");

        Assert.assertEquals(cardManagementApiService.activate(activateExternalCardRequest).getStatusCode().toString(), "200");

        log.info("End of " + testClass + "_testActivate");
    }

    @Test
    public void testActivate_FailResponse() {
        log.info("Start of " + testClass + "_testActivate_FailResponse");

        activateExternalCardRequest = ActivateExternalCardRequestMock.getInstance().createMockActivateExternalCardRequest("000", "456");

        Assert.assertEquals(cardManagementApiService.activate(activateExternalCardRequest).getStatusCode().toString(), "401");

        log.info("End of " + testClass + "_testActivate_FailResponse");
    }

    @Test
    public void testIsInUse() {
        log.info("Start of " + testClass + "_testIsInUse");

        checkCardInUseRequest = CheckCardInUseRequestMock.getInstance().createMockCheckCardInUseRequest("000", "456");

        Assert.assertEquals(cardManagementApiService.isInUse(checkCardInUseRequest).getStatusCode().toString(), "200");

        log.info("End of " + testClass + "_testIsInUse");
    }

    @Test
    public void testIsInUse_FailResponse() {
        log.info("Start of " + testClass + "_testIsInUse_FailResponse");

        checkCardInUseRequest = CheckCardInUseRequestMock.getInstance().createMockCheckCardInUseRequest("123", "456");

        Assert.assertEquals(cardManagementApiService.isInUse(checkCardInUseRequest).getStatusCode().toString(), "401");

        log.info("End of " + testClass + "_testIsInUse_FailResponse");
    }

    @Test
    public void testBalanceList() {
        log.info("Start of " + testClass + "_testBalanceList");

        listExternalCardRequest = ListExternalCardRequestMock.getInstance().createMockListExternalCardRequest("123", "456");

        Assert.assertEquals(cardManagementApiService.balanceList(listExternalCardRequest).getStatusCode().toString(), "200");

        log.info("End of " + testClass + "_testBalanceList");
    }

    @Test
    public void testBalanceList_FailResponse() {
        log.info("Start of " + testClass + "_testBalanceList_FailResponse");

        listExternalCardRequest = ListExternalCardRequestMock.getInstance().createMockListExternalCardRequest("000", "456");

        Assert.assertEquals(cardManagementApiService.balanceList(listExternalCardRequest).getStatusCode().toString(), "401");

        log.info("End of " + testClass + "_testBalanceList_FailResponse");
    }
}
