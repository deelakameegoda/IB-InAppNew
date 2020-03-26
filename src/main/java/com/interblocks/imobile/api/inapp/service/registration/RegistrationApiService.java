package com.interblocks.imobile.api.inapp.service.registration;

import com.interblocks.imobile.api.inapp.model.RegisterUserRequest;
import com.interblocks.imobile.api.inapp.model.RegisterUserResponse;
import org.springframework.http.ResponseEntity;

public interface RegistrationApiService {

    ResponseEntity<RegisterUserResponse> registerUser(RegisterUserRequest registerUserRequest);
}
