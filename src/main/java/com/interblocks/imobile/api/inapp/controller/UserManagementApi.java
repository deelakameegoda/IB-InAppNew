package com.interblocks.imobile.api.inapp.controller;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.imobile.api.inapp.service.user.UserManagementApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/user")
public class UserManagementApi {

    private final UserManagementApiService userManagementApiService;

    @Autowired
    public UserManagementApi(UserManagementApiService userManagementApiService) {
        this.userManagementApiService = userManagementApiService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login", produces = "application/json")
    public ResponseEntity<UserLoginResponse> postLogin(ExternalUserLogin loginUserRequest) {
        return userManagementApiService.login(loginUserRequest);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/edit", produces = "application/json")
    public ResponseEntity<CustomerProfile> postEditWallet(CustomerProfileEditRequest customerProfileEditRequest) {
        return userManagementApiService.edit(customerProfileEditRequest);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/list", produces = "application/json")
    public ResponseEntity<CustomerProfile> postListWallet(ListWalletRequest listWalletRequest) {
        return userManagementApiService.list(listWalletRequest);
    }

}
