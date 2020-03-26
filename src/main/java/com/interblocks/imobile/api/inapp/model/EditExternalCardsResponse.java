package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

import java.util.List;

@Data
public class EditExternalCardsResponse {

    private String walletId;
    private List<CardManagementResponse> cardManagementResponseList;
    private String statusCode;
    private String ExtErrorCode;
    private String statusDescription;
    private String failReason;
}
