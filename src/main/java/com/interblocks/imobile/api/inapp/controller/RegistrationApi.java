package com.interblocks.imobile.api.inapp.controller;

import com.interblocks.imobile.api.inapp.model.RegisterUserRequest;
import com.interblocks.imobile.api.inapp.model.RegisterUserResponse;
import com.interblocks.imobile.api.inapp.service.registration.RegistrationApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/registration")
public class RegistrationApi {

    private final RegistrationApiService registrationApiService;

    @Autowired
    public RegistrationApi(RegistrationApiService registrationApiService) {
        this.registrationApiService = registrationApiService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/register", produces = "application/json")
    public ResponseEntity<RegisterUserResponse> postTransaction(RegisterUserRequest registerUserRequest) {
        return registrationApiService.registerUser(registerUserRequest);
    }
}
