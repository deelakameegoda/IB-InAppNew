package com.interblocks.imobile.api.inapp.service.user;

import com.interblocks.imobile.api.inapp.model.*;
import org.springframework.http.ResponseEntity;

public interface UserManagementApiService {

    ResponseEntity<UserLoginResponse> login(ExternalUserLogin loginUserRequest);

    ResponseEntity<CustomerProfile> edit(CustomerProfileEditRequest customerProfileEditRequest);

    ResponseEntity<CustomerProfile> list(ListWalletRequest listWalletRequest);
}
