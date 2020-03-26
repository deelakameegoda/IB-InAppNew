/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.service.user;

import com.interblocks.imobile.api.inapp.model.CustomerProfileEditRequest;
import com.interblocks.imobile.api.inapp.model.ExternalUserLogin;
import com.interblocks.imobile.api.inapp.model.ListWalletRequest;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 * @author Anusha Ariyathilaka @ Interblocks Ltd.
 */
public abstract class UserManagementApiService {

    public abstract Response login(ExternalUserLogin loginUserRequest)
            throws NotFoundException;

    public abstract Response edit(CustomerProfileEditRequest customerProfileEditRequest)
            throws NotFoundException;

    public abstract Response list(ListWalletRequest listWalletRequest)
            throws NotFoundException;

}
