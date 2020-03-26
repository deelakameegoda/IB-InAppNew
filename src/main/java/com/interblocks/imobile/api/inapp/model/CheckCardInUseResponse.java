package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

@Data
public class CheckCardInUseResponse {

    private String walletId;
    private String statusCode;
    private String extErrorCode;
    private String statusDescription;
    private String failReason;
}
