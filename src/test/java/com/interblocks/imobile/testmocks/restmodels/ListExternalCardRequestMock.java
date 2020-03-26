package com.interblocks.imobile.testmocks.restmodels;

import com.interblocks.imobile.api.inapp.model.ListExternalCardRequest;

public class ListExternalCardRequestMock {
    private static ListExternalCardRequestMock ourInstance = new ListExternalCardRequestMock();

    public static ListExternalCardRequestMock getInstance() {
        return ourInstance;
    }


    public ListExternalCardRequest createMockListExternalCardRequest(String walletId, String merchantId) {
        ListExternalCardRequest mockListExternalCardRequest = new ListExternalCardRequest();
        mockListExternalCardRequest.setWalletId(walletId);
        mockListExternalCardRequest.setMerchantId(merchantId);

        return mockListExternalCardRequest;
    }
}
