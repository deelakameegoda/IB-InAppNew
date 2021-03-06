package com.interblocks.imobile.api.inapp.controller;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.imobile.api.inapp.service.card.CardManagementApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/instrument")
public class CardManagementApi {

    private final CardManagementApiService cardManagementApiService;

    @Autowired
    public CardManagementApi(CardManagementApiService cardManagementApiService) {
        this.cardManagementApiService = cardManagementApiService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/add", produces = "application/json")
    public ResponseEntity<AddCardsResponse> postTransaction(@RequestBody AddCardsRequest cardInsertRequest) {
        return cardManagementApiService.add(cardInsertRequest);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/list", produces = "application/json")
    public ResponseEntity<ListExternalCardResponse> list(@RequestBody ListExternalCardRequest listExternalCardRequest) {
        return cardManagementApiService.list(listExternalCardRequest);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/list-all", produces = "application/json")
    public ResponseEntity<ListExternalCardResponse> listAll(@RequestBody ListExternalCardRequest listExternalCardRequest) {
        return cardManagementApiService.listAll(listExternalCardRequest);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/edit", produces = "application/json")
    public ResponseEntity<EditExternalCardsResponse> edit(@RequestBody EditExternalCardsRequest editExternalCardsRequest) {
        return cardManagementApiService.edit(editExternalCardsRequest);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/delete", produces = "application/json")
    public ResponseEntity<DeleteExternalCardResponse> delete(@RequestBody DeleteExternalCardRequest deleteCardRequest) {
        return cardManagementApiService.delete(deleteCardRequest);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/activate", produces = "application/json")
    public ResponseEntity<ActivateExternalCardResponse> activate(@RequestBody ActivateExternalCardRequest activateCardRequest) {
        return cardManagementApiService.activate(activateCardRequest);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/isused", produces = "application/json")
    public ResponseEntity<CheckCardInUseResponse> isInUse(@RequestBody CheckCardInUseRequest checkCardInUseRequest) {
        return cardManagementApiService.isInUse(checkCardInUseRequest);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/balance-list", produces = "application/json")
    public ResponseEntity<BalanceList> balanceList(@RequestBody ListExternalCardRequest balanceListRequest) {
        return cardManagementApiService.balanceList(balanceListRequest);
    }
}
