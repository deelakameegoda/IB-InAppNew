package com.interblocks.imobile.testmocks.restmodels;

import com.interblocks.imobile.api.inapp.model.ListWalletRequest;

public class ListWalletRequestMock {

    private static ListWalletRequestMock ourInstance = new ListWalletRequestMock();

    public static ListWalletRequestMock getInstance() {
        return ourInstance;
    }


    public ListWalletRequest createMockListWalletRequest(String walletId) {
        ListWalletRequest mockListWalletRequest = new ListWalletRequest();
        mockListWalletRequest.setWalletId(walletId);

        return mockListWalletRequest;
    }
}
