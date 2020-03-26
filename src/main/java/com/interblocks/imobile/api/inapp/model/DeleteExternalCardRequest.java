package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

@Data
public class DeleteExternalCardRequest {

    private String walletId;
    private String merchantId;
    private String CardRef;
}
