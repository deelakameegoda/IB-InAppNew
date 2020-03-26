/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.service.card;

import com.interblocks.imobile.api.inapp.model.*;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

/**
 * @author Anusha Ariyathilaka @ Interblocks Ltd.
 */
public abstract class CardManagementApiService {

    public abstract Response add(AddCardsRequest cardInsertRequest)
            throws NotFoundException;

    public abstract Response list(ListExternalCardRequest listExternalCardRequest)
            throws NotFoundException;

    public abstract Response edit(EditExternalCardsRequest editExternalCardsRequest)
            throws NotFoundException;

    public abstract Response delete(DeleteExternalCardRequest deleteCardRequest)
            throws NotFoundException;

    public abstract Response activate(ActivateExternalCardRequest activateCardRequest)
            throws NotFoundException;

    public abstract Response isInUse(CheckCardInUseRequest checkCardInUseRequest)
            throws NotFoundException;

    public abstract Response balanceList(ListExternalCardRequest balanceListRequest) throws NotFoundException;

    public abstract Response listAll(ListExternalCardRequest listExternalCardRequest) throws NotFoundException;
}
