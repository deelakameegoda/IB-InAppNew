package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

@Data
public class CustomerProfile {

    private String statusCode;
    private String extErrorCode;
    private String statusDescription;
    private String walletId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private String middleName;
    private String nic;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String countryCode;
    private String failReason;
}
