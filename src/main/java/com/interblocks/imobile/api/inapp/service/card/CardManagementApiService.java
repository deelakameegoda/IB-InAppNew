package com.interblocks.imobile.api.inapp.service.card;

import com.interblocks.imobile.api.inapp.model.*;
import org.springframework.http.ResponseEntity;

public interface CardManagementApiService {

    ResponseEntity<AddCardsResponse> add(AddCardsRequest cardInsertRequest);

    ResponseEntity<ListExternalCardResponse> list(ListExternalCardRequest listExternalCardRequest);

    ResponseEntity<ListExternalCardResponse> listAll(ListExternalCardRequest listExternalCardRequest);

    ResponseEntity<EditExternalCardsResponse> edit(EditExternalCardsRequest editExternalCardsRequest);

    ResponseEntity<DeleteExternalCardResponse> delete(DeleteExternalCardRequest deleteCardRequest);

    ResponseEntity<ActivateExternalCardResponse> activate(ActivateExternalCardRequest activateCardRequest);

    ResponseEntity<CheckCardInUseResponse> isInUse(CheckCardInUseRequest checkCardInUseRequest);

    ResponseEntity<BalanceList> balanceList(ListExternalCardRequest balanceListRequest);
}
