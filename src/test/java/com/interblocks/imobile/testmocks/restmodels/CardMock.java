package com.interblocks.imobile.testmocks.restmodels;

import com.interblocks.imobile.api.inapp.model.Card;

public class CardMock {
    private static CardMock ourInstance = new CardMock();

    public static CardMock getInstance() {
        return ourInstance;
    }


    public Card createMockAddCardsRequest(String cardNumber) {
        Card mockCard = new Card();
        mockCard.setCardNumber(cardNumber);
        mockCard.setCardType("crdType");
        mockCard.setExpYear("2020");
        mockCard.setExpMonth("12");
        mockCard.setSecurityCode("0000");
        mockCard.setCardHolderName("userId");
        mockCard.setDefault(true);
        mockCard.setActive(true);
        mockCard.setCardRef("cardRef");
        mockCard.setMaskedCardNumber(cardNumber);
        mockCard.setIndex("1");
        mockCard.setExpired(false);
        mockCard.setActivationOTP("000");
        mockCard.setCardBin("crdBin");
        mockCard.setSecurityCode("0");

        return mockCard;
    }
}
