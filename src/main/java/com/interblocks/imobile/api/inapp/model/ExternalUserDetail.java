package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

import java.util.Date;

@Data
public class ExternalUserDetail {

    private String userId;
    private String userSuffix;
    private String bankCode;
    private String plainBankCode;
    private String refId;
    private String merchantId;
    private String dataEntity1;
    private String dataEntity2;
    private String dataEntity3;
    private String dataEntity4;
    private String dataEntity5;
    private String addedBy;
    private Date addedDate;
    private String modifiedBy;
    private Date modifiedDate;
}
