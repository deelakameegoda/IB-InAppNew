/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.controller;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.imobile.api.inapp.service.card.CardManagementApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/instrument")
@Service
public class CardManagementApi {

    @Autowired
    CardManagementApiService delegate;

    @POST
    @Path("/add")
    @Produces({"application/json"})
    public Response postTransaction(AddCardsRequest cardInsertRequest) {
        return delegate.add(cardInsertRequest);
    }

    @POST
    @Path("/list")
    @Produces({"application/json"})
    public Response list(ListExternalCardRequest listExternalCardRequest) {
        return delegate.list(listExternalCardRequest);
    }

    @POST
    @Path("/list-all")
    @Produces({"application/json"})
    public Response listAll(ListExternalCardRequest listExternalCardRequest) {
        return delegate.listAll(listExternalCardRequest);
    }


    @POST
    @Path("/edit")
    @Produces({"application/json"})
    public Response edit(EditExternalCardsRequest editExternalCardsRequest) {
        return delegate.edit(editExternalCardsRequest);
    }

    @POST
    @Path("/delete")
    @Produces({"application/json"})
    public Response delete(DeleteExternalCardRequest deleteCardRequest) {
        return delegate.delete(deleteCardRequest);
    }

    @POST
    @Path("/activate")
    @Produces({"application/json"})
    public Response activate(ActivateExternalCardRequest activateCardRequest) {
        return delegate.activate(activateCardRequest);
    }

    @POST
    @Path("/isused")
    @Produces({"application/json"})
    public Response isInUse(CheckCardInUseRequest checkCardInUseRequest) {
        return delegate.isInUse(checkCardInUseRequest);
    }

    @POST
    @Path("/balance-list")
    @Produces({"application/json"})
    public Response balanceList(ListExternalCardRequest balanceListRequest) {
        return delegate.balanceList(balanceListRequest);
    }
}
