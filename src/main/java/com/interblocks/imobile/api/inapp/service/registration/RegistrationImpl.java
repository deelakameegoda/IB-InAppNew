/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.service.registration;

import com.interblocks.imobile.api.inapp.model.RegisterUserRequest;
import com.interblocks.imobile.api.inapp.model.RegisterUserResponse;
import com.interblocks.imobile.subcomponents.inapp.InAppRegistrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 * @author Anusha Ariyathilaka
 */
@Log4j2
@Component
public class RegistrationImpl extends RegistrationApiService {

    @Autowired
    InAppRegistrationService objRegistrationBo;

    @Override
    public Response registerUser(RegisterUserRequest regUserRequest) throws NotFoundException {

        try {

            log.info("registerUser API Method Inoked.");

            RegisterUserResponse postInsertExternalWalletUser = objRegistrationBo.postInsertExternalWalletUser(regUserRequest);

            log.info("registerUser API Method Response recived from Bo.");

            if (postInsertExternalWalletUser.getStatusCode().equals("IB000")) {
                return Response.status(200).entity(postInsertExternalWalletUser).build();
            } else {
                return Response.status(401).entity(postInsertExternalWalletUser).build();
            }

        } catch (Exception e) {

            log.error("addUser Exception", e);

            return Response.status(401).entity(null).build();

        } //To change body of generated methods, choose Tools | Templates.
    }

}
