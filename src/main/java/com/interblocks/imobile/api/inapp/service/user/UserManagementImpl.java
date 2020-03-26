/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.service.user;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.imobile.subcomponents.inapp.InAppUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 * @author Anusha Ariyathilaka @ Interblocks Ltd.
 */
@Log4j2
@Component
public class UserManagementImpl extends UserManagementApiService {

    @Autowired
    InAppUserService objUserManagementBo;

    @Override
    public Response login(ExternalUserLogin loginUserRequest) throws NotFoundException {
        try {

            log.info("login API Method Inoked.");

            UserLoginResponse loginResponse = objUserManagementBo.postValidateUserResponse(loginUserRequest);

            log.info("login API Method Response recived from Bo.");

            if (loginResponse.getLoginStatusCode().equals("IB200")) {
                return Response.status(200).entity(loginResponse).build();
            } else {
                return Response.status(401).entity(loginResponse).build();
            }

        } catch (Exception e) {

            log.error("login Exception", e);

            return Response.status(401).entity(null).build();

        } //To change body of generated methods, choose Tools | Templates
    }

    @Override
    public Response edit(CustomerProfileEditRequest customerProfileEditRequest) throws NotFoundException {
        try {

            log.info("Edit wallet API Method Inoked.");

            CustomerProfile loginResponse = objUserManagementBo.postEditExternalWalletUser(customerProfileEditRequest);

            log.info("Edit wallet API Method Response recived from Bo.");

            if (loginResponse.getStatusCode().equals("IB200")) {
                return Response.status(200).entity(loginResponse).build();
            } else {
                return Response.status(401).entity(loginResponse).build();
            }

        } catch (Exception e) {

            log.error("Edit wallet Exception", e);

            return Response.status(401).entity(null).build();

        } //To change body of generated methods, choose Tools | Templates
    }

    @Override
    public Response list(ListWalletRequest listWalletRequest) throws NotFoundException {

        try {

            log.info("List wallet API Method Inoked.");

            CustomerProfile loginResponse = objUserManagementBo.postListExternalWalletUser(listWalletRequest);

            log.info("List wallet API Method Response recived from Bo.");

            if (loginResponse.getStatusCode().equals("IB200")) {
                return Response.status(200).entity(loginResponse).build();
            } else {
                return Response.status(401).entity(loginResponse).build();
            }

        } catch (Exception e) {

            log.error("List wallet Exception", e);

            return Response.status(401).entity(null).build();

        }
    }

}
