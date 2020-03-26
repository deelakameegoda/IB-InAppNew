package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

@Data
public class CheckCardInUseRequest {

    private String bankCode;
    private String merchantId;
    private String cardNo;
}
