package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

@Data
public class ListExternalCardRequest {

    private String walletId;
    private String merchantId;
}
