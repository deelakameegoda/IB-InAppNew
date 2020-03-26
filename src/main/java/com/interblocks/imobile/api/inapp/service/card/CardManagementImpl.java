package com.interblocks.imobile.api.inapp.service.card;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.imobile.subcomponents.inapp.InAppCardService;
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
public class CardManagementImpl extends CardManagementApiService {

    @Autowired
    InAppCardService objCardManagementBo;

    @Override
    public Response add(AddCardsRequest cardInsertRequest) throws NotFoundException {
        try {

            log.info("add card API Method Invoked.");

            AddCardsResponse loginResponse = objCardManagementBo.postInsertCardsToExternalRetailUser(cardInsertRequest);

            log.info("add card API Method Response received from Bo.");

            if (loginResponse.getStatusCode().equals("IB200")) {
                return Response.status(200).entity(loginResponse).build();
            } else {
                return Response.status(401).entity(loginResponse).build();
            }

        } catch (Exception e) {

            log.error("add card Exception", e);

            return Response.status(401).entity(null).build();

        } //To change body of generated methods, choose Tools | Templates
    }

    @Override
    public Response list(ListExternalCardRequest listExternalCardRequest) throws NotFoundException {
        try {

            log.info("list card API Method Inoked.");

            ListExternalCardResponse listResponse = objCardManagementBo.postListAllUserCards(listExternalCardRequest);

            log.info("list card API Method Response recived from Bo.");

            if (listResponse.getStatusCode().equals("IB200")) {
                return Response.status(200).entity(listResponse).build();
            } else {
                return Response.status(401).entity(listResponse).build();
            }

        } catch (Exception e) {

            log.error("list card Exception", e);

            return Response.status(401).entity(null).build();

        } //To change body of generated methods, choose Tools | Templates
    }

    @Override
    public Response listAll(ListExternalCardRequest listExternalCardRequest) throws NotFoundException {
        try {

            log.info("list all card API Method Inoked.");

            ListExternalCardResponse listResponse = objCardManagementBo.postListAllUserCardsWithDeleted(listExternalCardRequest);

            log.info("list all card API Method Response recived from Bo.");

            if (listResponse.getStatusCode().equals("IB200")) {
                return Response.status(200).entity(listResponse).build();
            } else {
                return Response.status(401).entity(listResponse).build();
            }

        } catch (Exception e) {

            log.error("list all card Exception", e);

            return Response.status(401).entity(null).build();

        } //To change body of generated methods, choose Tools | Templates
    }


    @Override
    public Response edit(EditExternalCardsRequest editExternalCardsRequest) throws NotFoundException {
        try {

            log.info("edit card API Method Inoked.");

            EditExternalCardsResponse editResponse = objCardManagementBo.postEditCards(editExternalCardsRequest);

            log.info("edit card API Method Response recived from Bo.");

            if (editResponse.getStatusCode().equals("IB200")) {
                return Response.status(200).entity(editResponse).build();
            } else {
                return Response.status(401).entity(editResponse).build();
            }

        } catch (Exception e) {

            log.error("edit card Exception", e);

            return Response.status(401).entity(null).build();

        } //To change body of generated methods, choose Tools | Templates
    }

    @Override
    public Response delete(DeleteExternalCardRequest deleteCardRequest) throws NotFoundException {
        try {

            log.info("delete card API Method Inoked.");

            DeleteExternalCardResponse deleteResponse = objCardManagementBo.postDeleteCards(deleteCardRequest);

            log.info("delete card API Method Response recived from Bo.");

            if (deleteResponse.getStatusCode().equals("IB200")) {
                return Response.status(200).entity(deleteResponse).build();
            } else {
                return Response.status(401).entity(deleteResponse).build();
            }

        } catch (Exception e) {

            log.error("delete card Exception", e);

            return Response.status(401).entity(null).build();

        }
    }

    @Override
    public Response activate(ActivateExternalCardRequest activateCardRequest) throws NotFoundException {
        try {

            log.info("Activate card API Method Inoked.");

            ActivateExternalCardResponse activateResponse = objCardManagementBo.postActivateCards(activateCardRequest);

            log.info("Activate card API Method Response recived from Bo.");

            if (activateResponse.getStatusCode().equals("IB200")) {
                return Response.status(200).entity(activateResponse).build();
            } else {
                return Response.status(401).entity(activateResponse).build();
            }

        } catch (Exception e) {

            log.error("Activate card Exception", e);

            return Response.status(401).entity(null).build();

        }
    }

    @Override
    public Response isInUse(CheckCardInUseRequest checkCardInUseRequest) throws NotFoundException {
        try {

            log.info("Is in use card API Method Invoked.");

            CheckCardInUseResponse isInUseResponse = objCardManagementBo.postCheckTheCardInUse(checkCardInUseRequest);

            log.info("Is in use card API Method Response received from Bo.");

            if (isInUseResponse.getStatusCode().equals("IB200")) {
                return Response.status(200).entity(isInUseResponse).build();
            } else {
                return Response.status(401).entity(isInUseResponse).build();
            }

        } catch (Exception e) {

            log.error("Is in use card Exception", e);

            return Response.status(401).entity(null).build();

        }
    }

    @Override
    public Response balanceList(ListExternalCardRequest balanceListRequest) throws NotFoundException {
        try {
            log.info("Balance List API Method Invoked.");

            BalanceList balanceList = objCardManagementBo.postGetBalanceList(balanceListRequest);

            log.info("Balance List API Method Response received from Bo.");

            if (balanceList.getStatusCode().equals("IB200")) {
                return Response.status(200).entity(balanceList).build();
            } else {
                return Response.status(401).entity(balanceList).build();
            }
        } catch (Exception e) {
            log.error("Balance List API Exception", e);
            return Response.status(401).entity(null).build();
        }
    }


}
