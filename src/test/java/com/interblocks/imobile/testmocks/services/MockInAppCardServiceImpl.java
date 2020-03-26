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
        return null;
    }

    @Override
    public DeleteExternalCardResponse postDeleteCards(DeleteExternalCardRequest deleteCardRequest) {
        return null;
    }

    @Override
    public ActivateExternalCardResponse postActivateCards(ActivateExternalCardRequest activateCardRequest) {
        return null;
    }

    @Override
    public CheckCardInUseResponse postCheckTheCardInUse(CheckCardInUseRequest checkCardInUseRequest) {
        return null;
    }

    @Override
    public List<BnkDmAcct> getUserExternalCardsWithSVC(String userid, String comid, String bankCode) {
        return null;
    }

    @Override
    public BalanceList postGetBalanceList(ListExternalCardRequest balanceListRequest) {
        return null;
    }

    private AddCardsResponse createAddCardsResponse(AddCardsRequest addCardsRequest) {
        AddCardsResponse addCardsResponse = new AddCardsResponse();
        if (addCardsRequest!=(null)){
            if(addCardsRequest.getWalletId().equals("000")){
                addCardsResponse.setStatusCode("401");
            }else{
                addCardsResponse.setStatusCode("IB200");
            }
            return addCardsResponse;
        } else{
            return null;
        }
    }

    private ListExternalCardResponse createListExternalCardResponse(ListExternalCardRequest listExternalCardRequest) {
        ListExternalCardResponse listExternalCardResponse = new ListExternalCardResponse();
        if (listExternalCardRequest!=(null)){
            if(listExternalCardRequest.getWalletId().equals("000")){
                listExternalCardResponse.setStatusCode("401");
            }else{
                listExternalCardResponse.setStatusCode("IB200");
            }
            return listExternalCardResponse;
        } else{
            return null;
        }
    }
}
