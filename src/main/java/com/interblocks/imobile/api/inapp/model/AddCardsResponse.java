package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

import java.util.List;

@Data
public class AddCardsResponse {

    private String walletId;
    private List<CardManagementResponse> cardManagementResponseList;
    private String statusCode;
    private String extErrorCode;
    private String statusDescription;
    private String failReason;
}
