/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.model;

/**
 * @author Anusha Ariyathilaka @ Interblocks Ltd.
 */
public class CardManagementResponse {

    private String statusCode;
    private String extErrorCode;
    private String statusDescription;
    private String exepation;
    private String cardNo;
    private String cardType;
    private String cardRef;
    private String cardHolderName;
    private boolean isDefault;
    private String failReason;
    private String activationOTP;
    private boolean isActive;
    private String maskedCardNumber;
    private String index;
    private boolean isExpired;
    private String cardBin;

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
     * @return the exepation
     */
    public String getExepation() {
        return exepation;
    }

    /**
     * @param exepation the exepation to set
     */
    public void setExepation(String exepation) {
        this.exepation = exepation;
    }

    /**
     * @return the cardNo
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * @param cardNo the cardNo to set
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * @return the cardType
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * @param cardType the cardType to set
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * @return the cardRef
     */
    public String getCardRef() {
        return cardRef;
    }

    /**
     * @param cardRef the cardRef to set
     */
    public void setCardRef(String cardRef) {
        this.cardRef = cardRef;
    }

    /**
     * @return the cardHolderName
     */
    public String getCardHolderName() {
        return cardHolderName;
    }

    /**
     * @param cardHolderName the cardHolderName to set
     */
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    /**
     * @return the isDefault
     */
    public boolean isIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
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
     * @return the activationOTP
     */
    public String getActivationOTP() {
        return activationOTP;
    }

    /**
     * @param activationOTP the activationOTP to set
     */
    public void setActivationOTP(String activationOTP) {
        this.activationOTP = activationOTP;
    }

    /**
     * @return the isActive
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the maskedCardNumber
     */
    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    /**
     * @param maskedCardNumber the maskedCardNumber to set
     */
    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    /**
     * @return the index
     */
    public String getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * @return the isExpired
     */
    public boolean isIsExpired() {
        return isExpired;
    }

    /**
     * @param isExpired the isExpired to set
     */
    public void setIsExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    /**
     * @return the cardBin
     */
    public String getCardBin() {
        return cardBin;
    }

    /**
     * @param cardBin the cardBin to set
     */
    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

}
