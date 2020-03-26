/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.controller;

import com.interblocks.imobile.api.inapp.model.CustomerProfileEditRequest;
import com.interblocks.imobile.api.inapp.model.ExternalUserLogin;
import com.interblocks.imobile.api.inapp.model.ListWalletRequest;
import com.interblocks.imobile.api.inapp.service.user.UserManagementApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author Anusha Ariyathilaka @ Interblocks Ltd.
 */
@Path("/user")
@Service
public class UserManagementApi {

    @Autowired
    UserManagementApiService delegate;

    @POST
    @Path("/login")
    @Produces({"application/json"})
    public Response postLogin(ExternalUserLogin loginUserRequest) {
        return delegate.login(loginUserRequest);
    }

    @POST
    @Path("/edit")
    @Produces({"application/json"})
    public Response postEditWallet(CustomerProfileEditRequest customerProfileEditRequest) {
        return delegate.edit(customerProfileEditRequest);
    }

    @POST
    @Path("/list")
    @Produces({"application/json"})
    public Response postListWallet(ListWalletRequest listWalletRequest) {
        return delegate.list(listWalletRequest);
    }

}
