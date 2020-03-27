package com.interblocks.imobile.testmocks.services;

import com.interblocks.imobile.api.inapp.model.RegisterUserRequest;
import com.interblocks.imobile.api.inapp.model.RegisterUserResponse;
import com.interblocks.imobile.subcomponents.inapp.InAppRegistrationService;

public class MockInAppRegistrationServiceImpl implements InAppRegistrationService {
    @Override
    public RegisterUserResponse postInsertExternalWalletUser(RegisterUserRequest oNewWalletUser) {
        return createRegisterUserResponse(oNewWalletUser);
    }

    public RegisterUserResponse createRegisterUserResponse(RegisterUserRequest registerUserRequest) {
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        if (registerUserRequest.getUserId().equals("userId")) {
            registerUserResponse.setStatusCode("IB000");
        } else {
            registerUserResponse.setStatusCode("401");
        }
        return registerUserResponse;
    }
}
