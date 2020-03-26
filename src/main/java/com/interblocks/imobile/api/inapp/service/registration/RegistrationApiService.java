/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.service.registration;

import com.interblocks.imobile.api.inapp.model.RegisterUserRequest;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 * @author Anusha Ariyathilaka
 */
public abstract class RegistrationApiService {
    public abstract Response registerUser(RegisterUserRequest regUserRequest)
            throws NotFoundException;
}
