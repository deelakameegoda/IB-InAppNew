package com.interblocks.imobile.testmocks.restmodels;

import com.interblocks.imobile.api.inapp.model.RegisterUserRequest;

public class RegisterUserRequestMock {
    private static RegisterUserRequestMock ourInstance = new RegisterUserRequestMock();

    public static RegisterUserRequestMock getInstance() {
        return ourInstance;
    }

    public RegisterUserRequest createMockRegisterUserRequest(String userId) {
        RegisterUserRequest mockRegisterUserRequest = new RegisterUserRequest();
        mockRegisterUserRequest.setUserId(userId);
        mockRegisterUserRequest.setMerchantId("123");
        mockRegisterUserRequest.setFirstName("firstName");
        mockRegisterUserRequest.setEmail("email");
        mockRegisterUserRequest.setMiddleName("middleName");
        mockRegisterUserRequest.setLastName("lastName");
        mockRegisterUserRequest.setAddressLine1("addressLine1");
        mockRegisterUserRequest.setAddressLine2("addressLine2");
        mockRegisterUserRequest.setAddressLine3("addressLine3");
        mockRegisterUserRequest.setAddressLine4("addressLine4");
        mockRegisterUserRequest.setMobilePhoneNo("mobilePhoneNo");
        mockRegisterUserRequest.setNicNumber("nicNumber");
        mockRegisterUserRequest.setBankCode("000");
        mockRegisterUserRequest.setCountryId("111");

        return mockRegisterUserRequest;
    }
}
