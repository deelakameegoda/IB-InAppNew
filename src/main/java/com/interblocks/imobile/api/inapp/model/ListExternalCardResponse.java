package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

import java.util.List;

@Data
public class ListExternalCardResponse {

    private String walletId;
    private List<Card> cardList;
    private String statusCode;
    private String extErrorCode;
    private String statusDescription;
    private String failReason;
}
