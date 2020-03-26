package com.interblocks.imobile.api.inapp.model;

import lombok.Data;

@Data
public class RegisterUserResponse {

    private String statusCode;
    private String statusDescription;
    private String extErrorCode;
    private String exception;
    private String failReason;
    private String email;
    private String firstName;
    private String lastName;
    private String walletId;
    private String token;
}
