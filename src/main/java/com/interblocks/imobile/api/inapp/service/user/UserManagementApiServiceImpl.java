package com.interblocks.imobile.api.inapp.service.user;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.imobile.subcomponents.inapp.InAppUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserManagementApiServiceImpl implements UserManagementApiService {

    private final InAppUserService inAppUserService;

    @Autowired
    public UserManagementApiServiceImpl(InAppUserService inAppUserService) {
        this.inAppUserService = inAppUserService;
    }

    @Override
    public ResponseEntity<UserLoginResponse> login(ExternalUserLogin loginUserRequest) {
        log.info("login API Method Invoked.");

        UserLoginResponse loginResponse = inAppUserService.postValidateUserResponse(loginUserRequest);

        log.info("login API Method Response received from Bo.");

        if (loginResponse.getLoginStatusCode().equals("IB200")) {
            return ResponseEntity.status(200).body(loginResponse);
        } else {
            return ResponseEntity.status(401).body(loginResponse);
        }
    }

    @Override
    public ResponseEntity<CustomerProfile> edit(CustomerProfileEditRequest customerProfileEditRequest) {

        log.info("Edit wallet API Method Invoked.");

        CustomerProfile loginResponse = inAppUserService.postEditExternalWalletUser(customerProfileEditRequest);

        log.info("Edit wallet API Method Response received from Bo.");

        if (loginResponse.getStatusCode().equals("IB200")) {
            return ResponseEntity.status(200).body(loginResponse);
        } else {
            return ResponseEntity.status(401).body(loginResponse);
        }
    }

    @Override
    public ResponseEntity<CustomerProfile> list(ListWalletRequest listWalletRequest) {

        log.info("List wallet API Method Invoked.");

        CustomerProfile loginResponse = inAppUserService.postListExternalWalletUser(listWalletRequest);

        log.info("List wallet API Method Response received from Bo.");

        if (loginResponse.getStatusCode().equals("IB200")) {
            return ResponseEntity.status(200).body(loginResponse);
        } else {
            return ResponseEntity.status(401).body(loginResponse);
        }
    }

}
