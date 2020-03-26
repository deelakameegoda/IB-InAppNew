package com.interblocks.imobile.api.inapp.service.registration;

import com.interblocks.imobile.api.inapp.model.RegisterUserRequest;
import com.interblocks.imobile.api.inapp.model.RegisterUserResponse;
import com.interblocks.imobile.subcomponents.inapp.InAppRegistrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;

@Log4j2
@Service
public class RegistrationApiServiceImpl implements RegistrationApiService {

    private final InAppRegistrationService inAppRegistrationService;

    @Autowired
    public RegistrationApiServiceImpl(InAppRegistrationService inAppRegistrationService) {
        this.inAppRegistrationService = inAppRegistrationService;
    }

    @Override
    public ResponseEntity<RegisterUserResponse> registerUser(RegisterUserRequest regUserRequest) throws NotFoundException {

        log.info("registerUser API Method Inoked.");

        RegisterUserResponse registerUserResponse = inAppRegistrationService.postInsertExternalWalletUser(regUserRequest);

        log.info("registerUser API Method Response recived from Bo.");

        if (registerUserResponse.getStatusCode().equals("IB000")) {
            return ResponseEntity.status(200).body(registerUserResponse);
        } else {
            return ResponseEntity.status(401).body(registerUserResponse);
        }
    }
}
