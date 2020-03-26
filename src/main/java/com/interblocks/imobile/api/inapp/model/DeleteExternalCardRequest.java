/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.model;

/**
 * @author IB
 */
public class DeleteExternalCardRequest {

    private String walletId;
    private String merchantId;
    private String CardRef;

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
     * @return the merchantId
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * @param merchantId the merchantId to set
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * @return the CardRef
     */
    public String getCardRef() {
        return CardRef;
    }

    /**
     * @param CardRef the CardRef to set
     */
    public void setCardRef(String CardRef) {
        this.CardRef = CardRef;
    }


}
