package com.interblocks.imobile.testmocks.services;

import com.interblocks.imobile.api.inapp.model.RegisterUserRequest;
import com.interblocks.imobile.api.inapp.model.RegisterUserResponse;
import com.interblocks.imobile.subcomponents.inapp.InAppRegistrationService;

public class MockInAppRegistrationServiceImpl implements InAppRegistrationService {
    @Override
    public RegisterUserResponse postInsertExternalWalletUser(RegisterUserRequest oNewWalletUser) {
        return null;
    }
}
