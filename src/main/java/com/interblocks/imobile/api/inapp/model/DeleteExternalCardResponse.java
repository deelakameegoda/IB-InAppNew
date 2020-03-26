/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.model;

/**
 * @author IB
 */
public class DeleteExternalCardResponse {

    private String walletId;
    private CardManagementResponse cardManagementResponse;
    private String statusCode;
    private String extErrorCode;
    private String statusDescription;
    private String failReason;

    /**
     * @return the walletId
     */
    public String getWalletId() {
        return walletId;
    }

    /**
     * @param walletId the walletId to set
     */
    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the extErrorCode
     */
    public String getExtErrorCode() {
        return extErrorCode;
    }

    /**
     * @param extErrorCode the extErrorCode to set
     */
    public void setExtErrorCode(String extErrorCode) {
        this.extErrorCode = extErrorCode;
    }

    /**
     * @return the statusDescription
     */
    public String getStatusDescription() {
        return statusDescription;
    }

    /**
     * @param statusDescription the statusDescription to set
     */
    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    /**
     * @return the failReason
     */
    public String getFailReason() {
        return failReason;
    }

    /**
     * @param failReason the failReason to set
     */
    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    /**
     * @return the cardManagementResponse
     */
    public CardManagementResponse getCardManagementResponse() {
        return cardManagementResponse;
    }

    /**
     * @param cardManagementResponse the cardManagementResponse to set
     */
    public void setCardManagementResponse(CardManagementResponse cardManagementResponse) {
        this.cardManagementResponse = cardManagementResponse;
    }

}
