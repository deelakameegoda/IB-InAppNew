package com.interblocks.imobile.testmocks.restmodels;

import com.interblocks.imobile.api.inapp.model.DeleteExternalCardRequest;

public class DeleteExternalCardRequestMock {
    private static DeleteExternalCardRequestMock ourInstance = new DeleteExternalCardRequestMock();

    public static DeleteExternalCardRequestMock getInstance() {
        return ourInstance;
    }

    public DeleteExternalCardRequest createMockDeleteExternalCardRequest(String walletId, String merchantId) {
        DeleteExternalCardRequest mockDeleteExternalCardRequest = new DeleteExternalCardRequest();
        mockDeleteExternalCardRequest.setWalletId(walletId);
        mockDeleteExternalCardRequest.setMerchantId(merchantId);
        mockDeleteExternalCardRequest.setCardRef("cardRef");

        return mockDeleteExternalCardRequest;
    }
}
