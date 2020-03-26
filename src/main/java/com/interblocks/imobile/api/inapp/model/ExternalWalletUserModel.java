package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

@Data
public class ExternalWalletUserModel {


    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String mobilePhoneNo;
    private String email;
    private String nicNumber;
    private String bankCode;
    private String selectedCountryId;
    private String merchantId;
    private String extId;
    private String errorDesc;
}
