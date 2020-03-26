package com.interblocks.imobile.api.inapp.service.card;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.imobile.subcomponents.inapp.InAppCardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;

@Log4j2
@Service
public class CardManagementApiServiceImpl implements CardManagementApiService {

    private final InAppCardService inAppCardService;

    @Autowired
    public CardManagementApiServiceImpl(InAppCardService inAppCardService) {
        this.inAppCardService = inAppCardService;
    }

    @Override
    public ResponseEntity<AddCardsResponse> add(AddCardsRequest cardInsertRequest) throws NotFoundException {

        log.info("add card API Method Invoked.");

        AddCardsResponse loginResponse = inAppCardService.postInsertCardsToExternalRetailUser(cardInsertRequest);

        log.info("add card API Method Response received from Bo.");

        if (loginResponse.getStatusCode().equals("IB200")) {
            return ResponseEntity.status(200).body(loginResponse);
        } else {
            return ResponseEntity.status(401).body(loginResponse);
        }
    }

    @Override
    public ResponseEntity<ListExternalCardResponse> list(ListExternalCardRequest listExternalCardRequest) throws NotFoundException {
        log.info("list card API Method Invoked.");

        ListExternalCardResponse listExternalCardResponse = inAppCardService.postListAllUserCards(listExternalCardRequest);

        log.info("list card API Method Response received from Bo.");

        if (listExternalCardResponse.getStatusCode().equals("IB200")) {
            return ResponseEntity.status(200).body(listExternalCardResponse);
        } else {
            return ResponseEntity.status(401).body(listExternalCardResponse);
        }
    }

    @Override
    public ResponseEntity<ListExternalCardResponse> listAll(ListExternalCardRequest listExternalCardRequest) throws NotFoundException {

        log.info("list all card API Method Invoked.");

        ListExternalCardResponse listExternalCardResponse = inAppCardService.postListAllUserCardsWithDeleted(listExternalCardRequest);

        log.info("list all card API Method Response received from Bo.");

        if (listExternalCardResponse.getStatusCode().equals("IB200")) {
            return ResponseEntity.status(200).body(listExternalCardResponse);
        } else {
            return ResponseEntity.status(401).body(listExternalCardResponse);
        }
    }


    @Override
    public ResponseEntity<EditExternalCardsResponse> edit(EditExternalCardsRequest editExternalCardsRequest) throws NotFoundException {

        log.info("edit card API Method Invoked.");

        EditExternalCardsResponse editExternalCardsResponse = inAppCardService.postEditCards(editExternalCardsRequest);

        log.info("edit card API Method Response received from Bo.");

        if (editExternalCardsResponse.getStatusCode().equals("IB200")) {
            return ResponseEntity.status(200).body(editExternalCardsResponse);
        } else {
            return ResponseEntity.status(401).body(editExternalCardsResponse);
        }
    }

    @Override
    public ResponseEntity<DeleteExternalCardResponse> delete(DeleteExternalCardRequest deleteCardRequest) throws NotFoundException {

        log.info("delete card API Method Invoked.");

        DeleteExternalCardResponse deleteExternalCardResponse = inAppCardService.postDeleteCards(deleteCardRequest);

        log.info("delete card API Method Response received from Bo.");

        if (deleteExternalCardResponse.getStatusCode().equals("IB200")) {
            return ResponseEntity.status(200).body(deleteExternalCardResponse);
        } else {
            return ResponseEntity.status(401).body(deleteExternalCardResponse);
        }
    }

    @Override
    public ResponseEntity<ActivateExternalCardResponse> activate(ActivateExternalCardRequest activateCardRequest) throws NotFoundException {

        log.info("Activate card API Method Invoked.");

        ActivateExternalCardResponse activateExternalCardResponse = inAppCardService.postActivateCards(activateCardRequest);

        log.info("Activate card API Method Response received from Bo.");

        if (activateExternalCardResponse.getStatusCode().equals("IB200")) {
            return ResponseEntity.status(200).body(activateExternalCardResponse);
        } else {
            return ResponseEntity.status(401).body(activateExternalCardResponse);
        }

    }

    @Override
    public ResponseEntity<CheckCardInUseResponse> isInUse(CheckCardInUseRequest checkCardInUseRequest) throws NotFoundException {

        log.info("Is in use card API Method Invoked.");

        CheckCardInUseResponse checkCardInUseResponse = inAppCardService.postCheckTheCardInUse(checkCardInUseRequest);

        log.info("Is in use card API Method Response received from Bo.");

        if (checkCardInUseResponse.getStatusCode().equals("IB200")) {
            return ResponseEntity.status(200).body(checkCardInUseResponse);
        } else {
            return ResponseEntity.status(401).body(checkCardInUseResponse);
        }

    }

    @Override
    public ResponseEntity<BalanceList> balanceList(ListExternalCardRequest balanceListRequest) throws NotFoundException {
        log.info("Balance List API Method Invoked.");

        BalanceList balanceList = inAppCardService.postGetBalanceList(balanceListRequest);

        log.info("Balance List API Method Response received from Bo.");

        if (balanceList.getStatusCode().equals("IB200")) {
            return ResponseEntity.status(200).body(balanceList);
        } else {
            return ResponseEntity.status(401).body(balanceList);
        }
    }
}
