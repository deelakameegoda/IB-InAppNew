package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

@Data
public class ActivateExternalCardResponse {

    private String walletId;
    private CardManagementResponse cardManagementResponse;
    private String statusCode;
    private String extErrorCode;
    private String statusDescription;
    private String failReason;
}
