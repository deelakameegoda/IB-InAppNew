package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

@Data
public class CardManagementResponse {

    private String statusCode;
    private String extErrorCode;
    private String statusDescription;
    private String exepation;
    private String cardNo;
    private String cardType;
    private String cardRef;
    private String cardHolderName;
    private boolean isDefault;
    private String failReason;
    private String activationOTP;
    private boolean isActive;
    private String maskedCardNumber;
    private String index;
    private boolean isExpired;
    private String cardBin;
}
