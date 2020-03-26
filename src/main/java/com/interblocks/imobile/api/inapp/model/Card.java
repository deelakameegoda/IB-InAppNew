package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

@Data
public class Card {

    private String cardNumber;
    private String cardType;
    private String expYear;
    private String expMonth;
    private String securityCode;
    private String cardHolderName;
    private boolean isDefault;
    private boolean isActive;
    private String cardRef;
    private String maskedCardNumber;
    private String index;
    private boolean isExpired;
    private String activationOTP;
    private String cardBin;
    private String status;
}
