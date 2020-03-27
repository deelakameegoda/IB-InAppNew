package com.interblocks.imobile.testmocks.restmodels;

import com.interblocks.imobile.api.inapp.model.Card;
import com.interblocks.imobile.api.inapp.model.EditExternalCardsRequest;

import java.util.ArrayList;
import java.util.List;

public class EditExternalCardsRequestMock {
    private static EditExternalCardsRequestMock ourInstance = new EditExternalCardsRequestMock();

    public static EditExternalCardsRequestMock getInstance() {
        return ourInstance;
    }

    Card card;
    List<Card> cards = new ArrayList<>();

    public EditExternalCardsRequest createMockEditExternalCardsRequest(String walletId, String merchantId) {
        EditExternalCardsRequest mockEditExternalCardsRequest = new EditExternalCardsRequest();
        card = CardMock.getInstance().createMockAddCardsRequest("123456");
        cards.add(card);
        mockEditExternalCardsRequest.setWalletId(walletId);
        mockEditExternalCardsRequest.setMerchantId(merchantId);
        mockEditExternalCardsRequest.setCardList(cards);

        return mockEditExternalCardsRequest;
    }
}
