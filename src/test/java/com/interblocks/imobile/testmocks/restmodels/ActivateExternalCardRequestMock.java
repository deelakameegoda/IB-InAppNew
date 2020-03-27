package com.interblocks.imobile.testmocks.restmodels;

import com.interblocks.imobile.api.inapp.model.ActivateExternalCardRequest;

public class ActivateExternalCardRequestMock {
    private static ActivateExternalCardRequestMock ourInstance = new ActivateExternalCardRequestMock();

    public static ActivateExternalCardRequestMock getInstance() {
        return ourInstance;
    }

    public ActivateExternalCardRequest createMockActivateExternalCardRequest(String walletId, String merchantId) {
        ActivateExternalCardRequest mockActivateExternalCardRequest = new ActivateExternalCardRequest();
        mockActivateExternalCardRequest.setWalletId(walletId);
        mockActivateExternalCardRequest.setMerchantId(merchantId);
        mockActivateExternalCardRequest.setCardRef("cardRef");
        mockActivateExternalCardRequest.setActivationCode("000");

        return mockActivateExternalCardRequest;
    }
}
