package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

@Data
public class UserLoginResponse {
    private String loginStatus;
    private String loginStatusDescription;
    private String loginStatusCode;
    private String token;
    private String loginComName;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String countryCode;
    private String email;
    private String middleName;
    private String nic;
    private boolean forcePassword;
    private boolean isTocApproved;
}
