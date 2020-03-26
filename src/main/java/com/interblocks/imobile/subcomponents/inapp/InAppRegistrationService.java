package com.interblocks.imobile.subcomponents.inapp;

import com.interblocks.imobile.api.inapp.model.RegisterUserRequest;
import com.interblocks.imobile.api.inapp.model.RegisterUserResponse;

public interface InAppRegistrationService {

    RegisterUserResponse postInsertExternalWalletUser(RegisterUserRequest oNewWalletUser);
}
