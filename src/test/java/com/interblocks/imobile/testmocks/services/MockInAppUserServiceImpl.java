package com.interblocks.imobile.testmocks.services;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.imobile.subcomponents.inapp.InAppUserService;
import com.interblocks.iwallet.model.BnkDlUsr;

public class MockInAppUserServiceImpl implements InAppUserService {
    @Override
    public UserLoginResponse postValidateUserResponse(ExternalUserLogin User) {
        return createUserLoginResponse(User);
    }

    @Override
    public BnkDlUsr getExternalUserData(String walletId, String merchantId) {
        return null;
    }

    @Override
    public BnkDlUsr getExternalUserDataByUserId(String userId, String merchantId) {
        return null;
    }

    @Override
    public CustomerProfile postEditExternalWalletUser(CustomerProfileEditRequest customerProfileEditRequest) {
        return createUserLoginResponse(customerProfileEditRequest);
    }

    @Override
    public CustomerProfile postListExternalWalletUser(ListWalletRequest listWalletRequest) {
        return createUserLoginResponse(listWalletRequest);
    }

    @Override
    public ExternalWalletUserModel ListExternalUserRequest(String userId, String bankCode) {
        return null;
    }

    public UserLoginResponse createUserLoginResponse(ExternalUserLogin externalUserLogin){
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        if (externalUserLogin.getWalletId().equals("000")) {
            userLoginResponse.setLoginStatusCode("IB200");
        } else {
            userLoginResponse.setLoginStatusCode("401");
        }
        return userLoginResponse;
    }

    public CustomerProfile createUserLoginResponse(CustomerProfileEditRequest customerProfileEditRequest){
        CustomerProfile customerProfile = new CustomerProfile();
        if (customerProfileEditRequest.getWalletId().equals("000")) {
            customerProfile.setStatusCode("IB200");
        } else {
            customerProfile.setStatusCode("401");
        }
        return customerProfile;
    }

    public CustomerProfile createUserLoginResponse(ListWalletRequest listWalletRequest){
        CustomerProfile customerProfile = new CustomerProfile();
        if (listWalletRequest.getWalletId().equals("000")) {
            customerProfile.setStatusCode("IB200");
        } else {
            customerProfile.setStatusCode("401");
        }
        return customerProfile;
    }
}
