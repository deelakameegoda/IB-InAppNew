package com.interblocks.imobile.testmocks.services;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.imobile.subcomponents.inapp.InAppCardService;
import com.interblocks.iwallet.model.BnkDmAcct;

import java.util.Date;
import java.util.List;

public class MockInAppCardServiceImpl implements InAppCardService {
    @Override
    public AddCardsResponse postInsertCardsToExternalRetailUser(AddCardsRequest insertCardRequest) {
        return createAddCardsResponse(insertCardRequest);
    }

    @Override
    public int insertCardToRetailUser(String comid, String accountid, String status, String actype, String customerid, Date acc_accessfromdate, Date acc_accesstodate, String dailylimit, String monthlylimit, String addedby, Date addeddate, String modifyby, Date modifydate, String userid, String bankCode, boolean isDefault, String cardHolderName, String expMonth, String expYear, boolean isActivationRequired, Date otpExpTime, String maskedCardNo, int index, String activationOTP, String cardRef, boolean isSystemGenerated, String cardBin) {
        return 0;
    }

    @Override
    public ListExternalCardResponse postListAllUserCardsWithDeleted(ListExternalCardRequest listExternalCardRequest) {
        return createListExternalCardResponse(listExternalCardRequest);
    }

    @Override
    public ListExternalCardResponse postListAllUserCards(ListExternalCardRequest listExternalCardRequest) {
        return createListExternalCardResponse(listExternalCardRequest);
    }

    @Override
    public EditExternalCardsResponse postEditCards(EditExternalCardsRequest editCardRequest) {
        return createEditExternalCardsResponse(editCardRequest);
    }

    @Override
    public DeleteExternalCardResponse postDeleteCards(DeleteExternalCardRequest deleteCardRequest) {
        return createDeleteExternalCardResponse(deleteCardRequest);
    }

    @Override
    public ActivateExternalCardResponse postActivateCards(ActivateExternalCardRequest activateCardRequest) {
        return createActivateExternalCardResponse(activateCardRequest);
    }

    @Override
    public CheckCardInUseResponse postCheckTheCardInUse(CheckCardInUseRequest checkCardInUseRequest) {
        return createCheckCardInUseResponse(checkCardInUseRequest);
    }

    @Override
    public List<BnkDmAcct> getUserExternalCardsWithSVC(String userid, String comid, String bankCode) {
        return null;
    }

    @Override
    public BalanceList postGetBalanceList(ListExternalCardRequest balanceListRequest) {
        return createBalanceList(balanceListRequest);
    }

    private AddCardsResponse createAddCardsResponse(AddCardsRequest addCardsRequest) {
        AddCardsResponse addCardsResponse = new AddCardsResponse();
        if (addCardsRequest.getWalletId().equals("000")) {
            addCardsResponse.setStatusCode("401");
        } else {
            addCardsResponse.setStatusCode("IB200");
        }
        return addCardsResponse;

    }

    private ListExternalCardResponse createListExternalCardResponse(ListExternalCardRequest listExternalCardRequest) {
        ListExternalCardResponse listExternalCardResponse = new ListExternalCardResponse();
        if (listExternalCardRequest.getWalletId().equals("000")) {
            listExternalCardResponse.setStatusCode("401");
        } else {
            listExternalCardResponse.setStatusCode("IB200");
        }
        return listExternalCardResponse;

    }

    private EditExternalCardsResponse createEditExternalCardsResponse(EditExternalCardsRequest editExternalCardsRequest) {
        EditExternalCardsResponse editExternalCardsResponse = new EditExternalCardsResponse();
        if (editExternalCardsRequest.getWalletId().equals("000")) {
            editExternalCardsResponse.setStatusCode("401");
        } else {
            editExternalCardsResponse.setStatusCode("IB200");
        }
        return editExternalCardsResponse;

    }

    private DeleteExternalCardResponse createDeleteExternalCardResponse(DeleteExternalCardRequest deleteExternalCardRequest) {
        DeleteExternalCardResponse deleteExternalCardResponse = new DeleteExternalCardResponse();
        if (deleteExternalCardRequest.getWalletId().equals("000")) {
            deleteExternalCardResponse.setStatusCode("401");
        } else {
            deleteExternalCardResponse.setStatusCode("IB200");
        }
        return deleteExternalCardResponse;

    }

    private ActivateExternalCardResponse createActivateExternalCardResponse(ActivateExternalCardRequest activateExternalCardRequest) {
        ActivateExternalCardResponse activateExternalCardResponse = new ActivateExternalCardResponse();
        if (activateExternalCardRequest.getWalletId().equals("000")) {
            activateExternalCardResponse.setStatusCode("401");
        } else {
            activateExternalCardResponse.setStatusCode("IB200");
        }
        return activateExternalCardResponse;

    }

    private CheckCardInUseResponse createCheckCardInUseResponse(CheckCardInUseRequest checkCardInUseRequest) {
        CheckCardInUseResponse checkCardInUseResponse = new CheckCardInUseResponse();
        if (checkCardInUseRequest.getBankCode().equals("000")) {
            checkCardInUseResponse.setStatusCode("IB200");
        } else {
            checkCardInUseResponse.setStatusCode("401");
        }
        return checkCardInUseResponse;

    }

    private BalanceList createBalanceList(ListExternalCardRequest listExternalCardRequest) {
        BalanceList balanceList = new BalanceList();
        if (listExternalCardRequest.getWalletId().equals("000")) {
            balanceList.setStatusCode("401");
        } else {
            balanceList.setStatusCode("IB200");
        }
        return balanceList;
    }
}
