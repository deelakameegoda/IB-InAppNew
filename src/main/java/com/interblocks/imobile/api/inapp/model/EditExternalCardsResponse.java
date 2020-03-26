/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.model;

import java.util.List;

/**
 * @author IB
 */
public class EditExternalCardsResponse {

    private String walletId;

    private List<CardManagementResponse> cardManagementResponseList;

    private String statusCode;

    private String ExtErrorCode;

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
     * @return the cardManagementResponseList
     */
    public List<CardManagementResponse> getCardManagementResponseList() {
        return cardManagementResponseList;
    }

    /**
     * @param cardManagementResponseList the cardManagementResponseList to set
     */
    public void setCardManagementResponseList(List<CardManagementResponse> cardManagementResponseList) {
        this.cardManagementResponseList = cardManagementResponseList;
    }

    /**
     * @return the ctatusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param ctatusCode the ctatusCode to set
     */
    public void setStatusCode(String ctatusCode) {
        this.statusCode = ctatusCode;
    }

    /**
     * @return the ExtErrorCode
     */
    public String getExtErrorCode() {
        return ExtErrorCode;
    }

    /**
     * @param ExtErrorCode the ExtErrorCode to set
     */
    public void setExtErrorCode(String ExtErrorCode) {
        this.ExtErrorCode = ExtErrorCode;
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

}
