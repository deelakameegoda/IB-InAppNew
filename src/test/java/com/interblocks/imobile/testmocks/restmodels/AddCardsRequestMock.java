package com.interblocks.imobile.testmocks.restmodels;

import com.interblocks.imobile.api.inapp.model.AddCardsRequest;
import com.interblocks.imobile.api.inapp.model.Card;

import java.util.ArrayList;
import java.util.List;

public class AddCardsRequestMock {
    private static AddCardsRequestMock ourInstance = new AddCardsRequestMock();

    public static AddCardsRequestMock getInstance() {
        return ourInstance;
    }

    Card card;
    List<Card> cards = new ArrayList<>();

    public AddCardsRequest createMockAddCardsRequest(String walletId, String merchantId) {
        AddCardsRequest mockAddCardsRequest = new AddCardsRequest();
        card = CardMock.getInstance().createMockAddCardsRequest("123456");
        cards.add(card);
        mockAddCardsRequest.setWalletId(walletId);
        mockAddCardsRequest.setMerchantId(merchantId);
        mockAddCardsRequest.setCardList(cards);

        return mockAddCardsRequest;
    }
}
