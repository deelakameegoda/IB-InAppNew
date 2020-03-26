package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

@Data
public class ActivateExternalCardRequest {

    private String walletId;
    private String merchantId;
    private String cardRef;
    private String activationCode;
}
