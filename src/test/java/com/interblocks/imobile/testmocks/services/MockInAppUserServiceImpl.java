package com.interblocks.imobile.testmocks.services;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.imobile.subcomponents.inapp.InAppUserService;
import com.interblocks.iwallet.model.BnkDlUsr;

public class MockInAppUserServiceImpl implements InAppUserService {
    @Override
    public UserLoginResponse postValidateUserResponse(ExternalUserLogin User) {
        return null;
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
        return null;
    }

    @Override
    public CustomerProfile postListExternalWalletUser(ListWalletRequest listWalletRequest) {
        return null;
    }

    @Override
    public ExternalWalletUserModel ListExternalUserRequest(String userId, String bankCode) {
        return null;
    }
}
