package com.interblocks.imobile.testmocks.restmodels;

import com.interblocks.imobile.api.inapp.model.CheckCardInUseRequest;

public class CheckCardInUseRequestMock {
    private static CheckCardInUseRequestMock ourInstance = new CheckCardInUseRequestMock();

    public static CheckCardInUseRequestMock getInstance() {
        return ourInstance;
    }

    public CheckCardInUseRequest createMockCheckCardInUseRequest(String bnkCode, String merchantId) {
        CheckCardInUseRequest mockCheckCardInUseRequest = new CheckCardInUseRequest();
        mockCheckCardInUseRequest.setBankCode(bnkCode);
        mockCheckCardInUseRequest.setMerchantId(merchantId);
        mockCheckCardInUseRequest.setCardNo("123456789");

        return mockCheckCardInUseRequest;
    }
}
