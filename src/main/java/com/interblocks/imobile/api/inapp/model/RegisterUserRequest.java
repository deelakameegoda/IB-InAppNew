package com.interblocks.imobile.api.inapp.model;

public class RegisterUserRequest {

    private String userId;
    private String merchantId;
    private String firstName;
    private String email;
    private String middleName;
    private String lastName;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String mobilePhoneNo;
    private String nicNumber;
    private String bankCode;
    private String countryId;

    /**
     * Returns "IB200" for valid objects
     *
     * @return
     */
    public RegisterUserResponse validate() {
        RegisterUserResponse regUserResp = new RegisterUserResponse();

        if (this.getUserId() == null || this.getUserId().isEmpty()) {

            regUserResp.setException("Service error.Bad Request.");
            regUserResp.setStatusCode("IB400");
            regUserResp.setStatusDescription("Service error.Bad Request.");
            regUserResp.setFailReason("User Id canot be null or empty.");
            regUserResp.setExtErrorCode("ERR_25_76");

            return regUserResp;
        }

        if (this.merchantId == null || this.merchantId.isEmpty()) {
            regUserResp.setException("Service error.Bad Request.");
            regUserResp.setStatusCode("IB400");
            regUserResp.setStatusDescription("Service error.Bad Request.");
            regUserResp.setFailReason("Merchant Id canot be null or empty.");
            regUserResp.setExtErrorCode("ERR_26_01");

            return regUserResp;
        }

        if (this.firstName == null || this.firstName.isEmpty()) {
            regUserResp.setException("Service error.Bad Request.");
            regUserResp.setStatusCode("IB400");
            regUserResp.setStatusDescription("Service error.Bad Request.");
            regUserResp.setFailReason("First Name canot be null or empty.");
            regUserResp.setExtErrorCode("ERR_26_02");

            return regUserResp;
        }

        if (this.email == null || this.email.isEmpty()) {
            regUserResp.setException("Service error.Bad Request.");
            regUserResp.setStatusCode("IB400");
            regUserResp.setStatusDescription("Service error.Bad Request.");
            regUserResp.setFailReason("Email canot be null or empty.");
            regUserResp.setExtErrorCode("ERR_27_14");

            return regUserResp;
        }

        regUserResp.setException("IB200");
        regUserResp.setStatusCode("IB200");
        return regUserResp;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * @param addressLine1 the addressLine1 to set
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * @return the addressLine2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * @param addressLine2 the addressLine2 to set
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * @return the addressLine3
     */
    public String getAddressLine3() {
        return addressLine3;
    }

    /**
     * @param addressLine3 the addressLine3 to set
     */
    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    /**
     * @return the addressLine4
     */
    public String getAddressLine4() {
        return addressLine4;
    }

    /**
     * @param addressLine4 the addressLine4 to set
     */
    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    /**
     * @return the mobilePhoneNo
     */
    public String getMobilePhoneNo() {
        return mobilePhoneNo;
    }

    /**
     * @param mobilePhoneNo the mobilePhoneNo to set
     */
    public void setMobilePhoneNo(String mobilePhoneNo) {
        this.mobilePhoneNo = mobilePhoneNo;
    }

    /**
     * @return the nicNumber
     */
    public String getNicNumber() {
        return nicNumber;
    }

    /**
     * @param nicNumber the nicNumber to set
     */
    public void setNicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
    }

    /**
     * @return the bankCode
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * @param bankCode the bankCode to set
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * @return the countryId
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

}
