package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

@Data
public class DeleteExternalCardResponse {

    private String walletId;
    private CardManagementResponse cardManagementResponse;
    private String statusCode;
    private String extErrorCode;
    private String statusDescription;
    private String failReason;
}
