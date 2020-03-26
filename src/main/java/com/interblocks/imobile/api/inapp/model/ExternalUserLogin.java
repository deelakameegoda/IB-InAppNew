/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.model;

/**
 * @author Anusha Ariyathilaka @ Interblocks Ltd.
 */
public class ExternalUserLogin {

    private String merchantId;
    private String walletId;

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

}
