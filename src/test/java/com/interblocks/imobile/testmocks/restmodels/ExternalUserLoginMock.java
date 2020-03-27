package com.interblocks.imobile.testmocks.restmodels;

import com.interblocks.imobile.api.inapp.model.ExternalUserLogin;

public class ExternalUserLoginMock {

    private static ExternalUserLoginMock ourInstance = new ExternalUserLoginMock();

    public static ExternalUserLoginMock getInstance() {
        return ourInstance;
    }


    public ExternalUserLogin createMockExternalUserLogin(String walletId) {
        ExternalUserLogin mockExternalUserLogin = new ExternalUserLogin();
        mockExternalUserLogin.setWalletId(walletId);
        mockExternalUserLogin.setMerchantId("654");

        return mockExternalUserLogin;
    }
}
