/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.controller;

import com.interblocks.imobile.api.inapp.model.RegisterUserRequest;
import com.interblocks.imobile.api.inapp.service.registration.RegistrationApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author Anusha Ariyathilaka
 */
@Path("/registration")
@Service
public class RegistrationApi {

    @Autowired
    RegistrationApiService delegate;

    @POST
    @Path("/register")
    @Produces({"application/json"})
    public Response postTransaction(RegisterUserRequest regUserRequest) {
        return delegate.registerUser(regUserRequest);
    }

}
