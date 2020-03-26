package com.interblocks.imobile.subcomponents.inapp;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.iwallet.model.BnkDmAcct;

import java.util.Date;
import java.util.List;

public interface InAppCardService {


    AddCardsResponse postInsertCardsToExternalRetailUser(AddCardsRequest insertCardRequest);


    int insertCardToRetailUser(String comid, String accountid,
                               String status,
                               String actype,
                               String customerid, Date acc_accessfromdate,
                               Date acc_accesstodate,
                               String dailylimit,
                               String monthlylimit, String addedby,
                               Date addeddate,
                               String modifyby,
                               Date modifydate, String userid,
                               String bankCode,
                               boolean isDefault, String cardHolderName,
                               String expMonth, String expYear,
                               boolean isActivationRequired, Date otpExpTime,
                               String maskedCardNo,
                               int index, String activationOTP,
                               String cardRef, boolean isSystemGenerated, String cardBin
    );


    ListExternalCardResponse postListAllUserCardsWithDeleted(ListExternalCardRequest listExternalCardRequest);

    ListExternalCardResponse postListAllUserCards(ListExternalCardRequest listExternalCardRequest);

    EditExternalCardsResponse postEditCards(EditExternalCardsRequest editCardRequest);

    DeleteExternalCardResponse postDeleteCards(DeleteExternalCardRequest deleteCardRequest);

    ActivateExternalCardResponse postActivateCards(ActivateExternalCardRequest activateCardRequest);

    CheckCardInUseResponse postCheckTheCardInUse(CheckCardInUseRequest checkCardInUseRequest);

    List<BnkDmAcct> getUserExternalCardsWithSVC(String userid, String comid, String bankCode);

    BalanceList postGetBalanceList(ListExternalCardRequest balanceListRequest);


}
