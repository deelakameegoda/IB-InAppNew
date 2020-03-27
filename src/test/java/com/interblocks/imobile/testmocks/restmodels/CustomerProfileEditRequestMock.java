package com.interblocks.imobile.testmocks.restmodels;

import com.interblocks.imobile.api.inapp.model.CustomerProfileEditRequest;

public class CustomerProfileEditRequestMock {

    private static CustomerProfileEditRequestMock ourInstance = new CustomerProfileEditRequestMock();

    public static CustomerProfileEditRequestMock getInstance() {
        return ourInstance;
    }


    public CustomerProfileEditRequest createMockCustomerProfileEditRequest(String walletId) {
        CustomerProfileEditRequest mockCustomerProfileEditRequest = new CustomerProfileEditRequest();
        mockCustomerProfileEditRequest.setWalletId(walletId);
        mockCustomerProfileEditRequest.setMerchantId("654");

        return mockCustomerProfileEditRequest;
    }
}
