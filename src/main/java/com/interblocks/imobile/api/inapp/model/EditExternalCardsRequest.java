package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

import java.util.List;

@Data
public class EditExternalCardsRequest {

    private String walletId;
    private String merchantId;
    private List<Card> cardList;
}
