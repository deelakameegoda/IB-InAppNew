package com.interblocks.imobile.subcomponents.inapp;


import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.imobile.api.inapp.oauth.InappOauth2Util;
import com.interblocks.iwallet.adaptor.rest.admin.IAdminRestClientInapp;
import com.interblocks.iwallet.adaptor.rest.admin.model.ExtrnlUserRequest2;
import com.interblocks.iwallet.adaptor.rest.admin.model.UserCommonResAdmin;
import com.interblocks.iwallet.model.BnkDlUsr;
import com.interblocks.iwallet.model.BnkDmAcct;
import com.interblocks.iwallet.oauth.PCIExtract;
import com.interblocks.iwallet.repository.UserLinkRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class InAppUserServiceImpl implements InAppUserService {
    @Autowired
    PCIExtract obPCIExtract;
    @Autowired
    private UserLinkRepository userLinkDao;
    @Autowired
    private InappOauth2Util inappOauth2Util;
    @Autowired
    private InAppCardService cardManagementBo;
    @Autowired
    private IAdminRestClientInapp iAdminRestClientInapp;

    @Override
    public UserLoginResponse postValidateUserResponse(ExternalUserLogin User) {
        UserLoginResponse ouserloginresponse = new UserLoginResponse();
        if (User != null) {
            try {
                if (User.getWalletId() == null || User.getWalletId().isEmpty()) {
                    ouserloginresponse.setLoginStatus("ERROR");
                    ouserloginresponse.setLoginStatusCode("IB400");
                    ouserloginresponse.setLoginStatusDescription("External wallet id cannot be null.");

                    log.error("External wallet id cannot be null.");

                    return ouserloginresponse;
                }

                BnkDlUsr existingUserLinkData = getExternalUserData(User.getWalletId(), User.getMerchantId());

                if (existingUserLinkData == null) {
                    ouserloginresponse.setLoginStatus("ERROR");
                    ouserloginresponse.setLoginStatusCode("IB400");
                    ouserloginresponse.setLoginStatusDescription("Requested record cannot be found.");

                    log.error("Requested record cannot be found.");
                    return ouserloginresponse;
                } else {
                    log.info("Requested record of user link data is found.");
                }

                try {
                    UserCommonResAdmin userResponse = iAdminRestClientInapp.getExtrnlUser(
                            existingUserLinkData.getBnkDlUsrPK().getBnkCode(),
                            existingUserLinkData.getBnkDlUsrPK().getUsrId()
                    );
                    if (userResponse != null) {

                        log.error("GetExtrnlUser of iAdmin auth service returns the status code of " + userResponse.getStaus());

                        if (userResponse.getStaus().equals("00")) {
                            ouserloginresponse.setAddressLine1(userResponse.getObjExtrArr().get(0).getAddressLine1());
                            ouserloginresponse.setAddressLine2(userResponse.getObjExtrArr().get(0).getAddressLine2());
                            ouserloginresponse.setAddressLine3(userResponse.getObjExtrArr().get(0).getAddressLine3());
                            ouserloginresponse.setAddressLine4(userResponse.getObjExtrArr().get(0).getAddressLine4());
                            ouserloginresponse.setCountryCode(userResponse.getObjExtrArr().get(0).getSelectedCountryId());
                            ouserloginresponse.setEmail(userResponse.getObjExtrArr().get(0).getEmail());
                            ouserloginresponse.setFirstName(userResponse.getObjExtrArr().get(0).getFirstName());
                            ouserloginresponse.setLastName(userResponse.getObjExtrArr().get(0).getLastName());
                            ouserloginresponse.setMiddleName(userResponse.getObjExtrArr().get(0).getMiddleName());
                            ouserloginresponse.setMobileNo(userResponse.getObjExtrArr().get(0).getMobilePhoneNo());
                            ouserloginresponse.setNic(userResponse.getObjExtrArr().get(0).getNICNumber());

                            ouserloginresponse.setToken(inappOauth2Util.generateToken(User.getWalletId(), existingUserLinkData.getBnkDlUsrPK().getBnkCode()));

                            ouserloginresponse.setLoginStatusCode("IB200");
                            ouserloginresponse.setLoginStatus("SUCCESS");
                            ouserloginresponse.setLoginStatusDescription("User logged successfully.");
                        } else {
                            ouserloginresponse.setLoginStatus("SYS_ERROR");
                            ouserloginresponse.setLoginStatusCode("IB603");
                            ouserloginresponse.setLoginStatusDescription("System error." + userResponse.getDescription() + ".");
                        }
                    } else {
                        log.error("GetExtrnlUser of iAdmin auth service returns null");

                        ouserloginresponse.setLoginStatus("SYS_ERROR");
                        ouserloginresponse.setLoginStatusCode("IB603");
                        ouserloginresponse.setLoginStatusDescription("System error.GetExtrnlUser of iAdmin auth service returns null");
                    }

                } catch (Exception e) {
                    log.error(e.getMessage(), e);

                    ouserloginresponse.setLoginStatus("SYS_ERROR");
                    ouserloginresponse.setLoginStatusCode("IB603");
                    ouserloginresponse.setLoginStatusDescription("System error." + e.getMessage());
                }
            } catch (Exception e) {
                ouserloginresponse.setLoginStatus("ERROR");
                ouserloginresponse.setLoginStatusCode("IB400");
                ouserloginresponse.setLoginStatusDescription(e.getMessage());
                log.error(e.getMessage());
                log.error("LoginUser Response Error");

            }
        } else {
            ouserloginresponse.setLoginStatus("ERROR");
            ouserloginresponse.setLoginStatusCode("IB400");
            log.error("LoginUser Response Null");

        }

        return ouserloginresponse;
    }

    @Override
    public BnkDlUsr getExternalUserData(String walletId, String merchantId) {
        BnkDlUsr existingUserLinkData = new BnkDlUsr();

        if (merchantId == null || merchantId.isEmpty() || merchantId.equals("NA")) {
            existingUserLinkData = userLinkDao.findByWalletId(walletId);
        } else {
            existingUserLinkData = userLinkDao.findByWalletIdAndMerchantId(walletId, merchantId);
        }

        return existingUserLinkData;
    }

    @Override
    public BnkDlUsr getExternalUserDataByUserId(String userId, String merchantId) {

        BnkDlUsr existingUserLinkData;

        existingUserLinkData = userLinkDao.findByUserIdAndMerchantId(userId, merchantId);

        return existingUserLinkData;
    }

    @Override
    public CustomerProfile postEditExternalWalletUser(CustomerProfileEditRequest customerProfileEditRequest) {

        CustomerProfile cusProfileToReturn = new CustomerProfile();
        cusProfileToReturn.setExtErrorCode("");

        if (customerProfileEditRequest.getWalletId() == null || customerProfileEditRequest.getWalletId().isEmpty()) {
            cusProfileToReturn.setStatusCode("IB400");
            cusProfileToReturn.setStatusDescription("Bad Request");
            cusProfileToReturn.setFailReason("External wallet id cannot be null.");
            cusProfileToReturn.setExtErrorCode("ERR_26_06");

            log.error("External wallet id cannot be null.");

            return cusProfileToReturn;
        }
        if (customerProfileEditRequest.getMerchantId() == null || customerProfileEditRequest.getMerchantId().isEmpty()) {
            cusProfileToReturn.setStatusCode("IB400");
            cusProfileToReturn.setStatusDescription("Bad Request");
            cusProfileToReturn.setFailReason("Merchant id cannot be null.");
            cusProfileToReturn.setExtErrorCode("ERR_26_07");

            log.error("Merchant id cannot be null.");

            return cusProfileToReturn;
        }

        log.info("Getting the external user details starts");

        BnkDlUsr existingUserLinkData = getExternalUserData(customerProfileEditRequest.getWalletId(), customerProfileEditRequest.getMerchantId());

        log.info("Getting the external user details finished");

        if (existingUserLinkData == null) {

            cusProfileToReturn.setStatusCode("IB400");
            cusProfileToReturn.setStatusDescription("Requested record cannot be found.");

            log.error("Requested record cannot be found.");
            return cusProfileToReturn;
        } else {
            log.info("Requested record of user link data is found.");
        }

        try {

            log.info("Getting the default card no....");

            List<BnkDmAcct> cardList = cardManagementBo.getUserExternalCardsWithSVC(existingUserLinkData.getBnkDlUsrPK().getUsrSfix(), existingUserLinkData.getBnkDlUsrPK().getUsrId(), existingUserLinkData.getBnkDlUsrPK().getBnkCode());

            BnkDmAcct defaultCard = new BnkDmAcct();

            for (BnkDmAcct bnkDmAccounts : cardList) {

                if (bnkDmAccounts.getBnkDlAcct().getDe05().equals("Y")) {
                    defaultCard = bnkDmAccounts;
                }

            }

            log.info("Getting the default card no finished");

            String defaultCardNo = "";

            if (defaultCard.getAcctNo() != null && !defaultCard.getAcctNo().isEmpty()) {
                defaultCardNo = defaultCard.getAcctNo();
            } else {
                log.info("No default account found.");
            }

            ExtrnlUserRequest2 walluserdeails = new ExtrnlUserRequest2();

            walluserdeails.setEmail(customerProfileEditRequest.getEmail());
            walluserdeails.setFirstName(customerProfileEditRequest.getFirstName());
            walluserdeails.setLastName(customerProfileEditRequest.getLastName());
            walluserdeails.setMiddleName(customerProfileEditRequest.getMiddleName());
            walluserdeails.setAddressLine1(customerProfileEditRequest.getAddressLine1());
            walluserdeails.setAddressLine2(customerProfileEditRequest.getAddressLine2());
            walluserdeails.setAddressLine3(customerProfileEditRequest.getAddressLine3());
            walluserdeails.setAddressLine4(customerProfileEditRequest.getAddressLine4());
            walluserdeails.setMobilePhoneNo(customerProfileEditRequest.getMobileNo());
            walluserdeails.setNICNumber(customerProfileEditRequest.getNic());
            walluserdeails.setSelectedCountryId(customerProfileEditRequest.getCountryCode());
            walluserdeails.setBankCode(existingUserLinkData.getBnkDlUsrPK().getBnkCode());
            walluserdeails.setUserId(existingUserLinkData.getBnkDlUsrPK().getUsrId());
            walluserdeails.setCardNo(defaultCardNo);
            walluserdeails.setMerchantId(customerProfileEditRequest.getMerchantId());

            log.info("iAdmin service method extrnlUserUpdate called.");

            UserCommonResAdmin userResponse = iAdminRestClientInapp.extrnlUserUpdate(walluserdeails);

            log.info("iAdmin service method extrnlUserUpdate finished.");

            if (userResponse == null) {

                log.info("iAdmin service method extrnlUserUpdate failed.Returns null");

                cusProfileToReturn.setAddressLine1("");
                cusProfileToReturn.setAddressLine2("");
                cusProfileToReturn.setAddressLine3("");
                cusProfileToReturn.setAddressLine4("");
                cusProfileToReturn.setCountryCode("");
                cusProfileToReturn.setEmail("");
                cusProfileToReturn.setFirstName("");
                cusProfileToReturn.setLastName("");
                cusProfileToReturn.setMiddleName("");
                cusProfileToReturn.setMobileNo("");
                cusProfileToReturn.setNic("");
                cusProfileToReturn.setWalletId(customerProfileEditRequest.getWalletId());
                cusProfileToReturn.setStatusCode("IB603");
                cusProfileToReturn.setStatusDescription("SYS_ERROR");
                cusProfileToReturn.setFailReason("iAdmin service method extrnlUserUpdate returns null.");
                return cusProfileToReturn;
            } else {
                if (!userResponse.getStaus().equals("00")) {

                    log.info("iAdmin service method extrnlUserUpdate failed.Returns the status code of " + userResponse.getStaus());

                    cusProfileToReturn.setAddressLine1("");
                    cusProfileToReturn.setAddressLine2("");
                    cusProfileToReturn.setAddressLine3("");
                    cusProfileToReturn.setAddressLine4("");
                    cusProfileToReturn.setCountryCode("");
                    cusProfileToReturn.setEmail("");
                    cusProfileToReturn.setFirstName("");
                    cusProfileToReturn.setLastName("");
                    cusProfileToReturn.setMiddleName("");
                    cusProfileToReturn.setMobileNo("");
                    cusProfileToReturn.setNic("");
                    cusProfileToReturn.setWalletId(customerProfileEditRequest.getWalletId());
                    cusProfileToReturn.setStatusCode("IB603");
                    cusProfileToReturn.setStatusDescription("SYS_ERROR");
                    cusProfileToReturn.setFailReason("iAdmin service method extrnlUserUpdate returns " + userResponse.getStaus() + ".");
                    return cusProfileToReturn;
                }
            }

            log.info("Getting the updated user's details from iAdmin service");
            log.info("GetExtrnlUser of iAdmin auth service called for " + walluserdeails.getUserId() + " of " + walluserdeails.getBankCode() + " started.");

            userResponse = iAdminRestClientInapp.getExtrnlUser(
                    existingUserLinkData.getBnkDlUsrPK().getBnkCode(),
                    existingUserLinkData.getBnkDlUsrPK().getUsrId()
            );

            log.info("GetExtrnlUser of iAdmin auth service called for " + walluserdeails.getUserId() + " of " + walluserdeails.getBankCode() + " finished.");

            if (userResponse == null || (!userResponse.getStaus().equals("00"))) {

                log.error("GetExtrnlUser of iAdmin auth service called for " + walluserdeails.getUserId() + " of " + walluserdeails.getBankCode() + " failed.");
                log.error("GetExtrnlUser of iAdmin auth service returns : {} ", userResponse == null ? "null" : userResponse.getStaus());

                cusProfileToReturn.setAddressLine1("");
                cusProfileToReturn.setAddressLine2("");
                cusProfileToReturn.setAddressLine3("");
                cusProfileToReturn.setAddressLine4("");
                cusProfileToReturn.setCountryCode("");
                cusProfileToReturn.setEmail("");
                cusProfileToReturn.setFirstName("");
                cusProfileToReturn.setLastName("");
                cusProfileToReturn.setMiddleName("");
                cusProfileToReturn.setMobileNo("");
                cusProfileToReturn.setNic("");
                cusProfileToReturn.setWalletId(customerProfileEditRequest.getWalletId());
                cusProfileToReturn.setStatusCode("IB603");
                cusProfileToReturn.setStatusDescription("SYS_ERROR");
                cusProfileToReturn.setFailReason("User updated successfully.Cannot list the user due to a system error.");

            } else {

                log.error("GetExtrnlUser of iAdmin auth service called for " + walluserdeails.getUserId() + " of " + walluserdeails.getBankCode() + " success.");

                cusProfileToReturn.setAddressLine1(userResponse.getObjExtrArr().get(0).getAddressLine1());
                cusProfileToReturn.setAddressLine2(userResponse.getObjExtrArr().get(0).getAddressLine2());
                cusProfileToReturn.setAddressLine3(userResponse.getObjExtrArr().get(0).getAddressLine3());
                cusProfileToReturn.setAddressLine4(userResponse.getObjExtrArr().get(0).getAddressLine4());
                cusProfileToReturn.setCountryCode(userResponse.getObjExtrArr().get(0).getSelectedCountryId());
                cusProfileToReturn.setEmail(userResponse.getObjExtrArr().get(0).getEmail());
                cusProfileToReturn.setFailReason("");
                cusProfileToReturn.setFirstName(userResponse.getObjExtrArr().get(0).getFirstName());
                cusProfileToReturn.setLastName(userResponse.getObjExtrArr().get(0).getLastName());
                cusProfileToReturn.setMiddleName(userResponse.getObjExtrArr().get(0).getMiddleName());
                cusProfileToReturn.setMobileNo(userResponse.getObjExtrArr().get(0).getMobilePhoneNo());
                cusProfileToReturn.setNic(userResponse.getObjExtrArr().get(0).getNICNumber());
                cusProfileToReturn.setStatusCode("IB200");
                cusProfileToReturn.setStatusDescription("Customer profile returns successfully.");
                cusProfileToReturn.setWalletId(customerProfileEditRequest.getWalletId());

            }

        } catch (Exception e) {

            log.error(e.getMessage(), e);

            cusProfileToReturn.setAddressLine1("");
            cusProfileToReturn.setAddressLine2("");
            cusProfileToReturn.setAddressLine3("");
            cusProfileToReturn.setAddressLine4("");
            cusProfileToReturn.setCountryCode("");
            cusProfileToReturn.setEmail("");
            cusProfileToReturn.setFirstName("");
            cusProfileToReturn.setLastName("");
            cusProfileToReturn.setMiddleName("");
            cusProfileToReturn.setMobileNo("");
            cusProfileToReturn.setNic("");
            cusProfileToReturn.setWalletId("");
            cusProfileToReturn.setStatusCode("IB603");
            cusProfileToReturn.setStatusDescription("SYS_ERROR");
            cusProfileToReturn.setFailReason("System error." + e.getMessage());
        }
        return cusProfileToReturn;
    }

    @Override
    public CustomerProfile postListExternalWalletUser(ListWalletRequest listWalletRequest) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        log.debug("PostListExternalWalletUserDetails() started.");

        CustomerProfile cusProfileToReturn = new CustomerProfile();

        if (listWalletRequest.getWalletId() == null || listWalletRequest.getWalletId().isEmpty()) {
            cusProfileToReturn.setStatusCode("IB400");
            cusProfileToReturn.setStatusDescription("Bad Request");
            cusProfileToReturn.setFailReason("External wallet id cannot be null.");
            cusProfileToReturn.setExtErrorCode("ERR_26_04");

            log.info("External wallet id cannot be null.");

            return cusProfileToReturn;
        }

        if (listWalletRequest.getMerchantId() == null || listWalletRequest.getMerchantId().isEmpty()) {
            cusProfileToReturn.setStatusCode("IB400");
            cusProfileToReturn.setStatusDescription("Bad Request");
            cusProfileToReturn.setFailReason("Merchant id cannot be null.");
            cusProfileToReturn.setExtErrorCode("ERR_26_05");

            log.info("Merchant id cannot be null.");

            return cusProfileToReturn;
        }

        log.debug("GetExternalUserDataByExternalUserId() started.");

        ExternalUserDetail extUserDetails = this.GetExternalUserDataByExternalUserId(listWalletRequest.getWalletId(), listWalletRequest.getMerchantId());

        log.debug("GetExternalUserDataByExternalUserId() finished.");

        if (extUserDetails.getRefId() == null || extUserDetails.getRefId().isEmpty()) {
            cusProfileToReturn.setStatusCode("IB400");
            cusProfileToReturn.setStatusDescription("Bad Request");
            cusProfileToReturn.setFailReason("Requested record cannot be found.");
            cusProfileToReturn.setExtErrorCode("");

            log.info("Requested record cannot be found.");
            return cusProfileToReturn;
        }

        try {

            log.debug("ListExternalUserRequest() started.");

            ExternalWalletUserModel extUser = this.ListExternalUserRequest(extUserDetails.getUserId(), extUserDetails.getPlainBankCode());

            log.debug("ListExternalUserRequest() finished.");

            if (extUser.getUserId() != null && !(extUser.getUserId().isEmpty())) {
                cusProfileToReturn.setAddressLine1(extUser.getAddressLine1());
                cusProfileToReturn.setAddressLine2(extUser.getAddressLine2());
                cusProfileToReturn.setAddressLine3(extUser.getAddressLine3());
                cusProfileToReturn.setAddressLine4(extUser.getAddressLine4());
                cusProfileToReturn.setCountryCode(extUser.getSelectedCountryId());
                cusProfileToReturn.setEmail(extUser.getEmail());
                cusProfileToReturn.setFailReason("");
                cusProfileToReturn.setFirstName(extUser.getFirstName());
                cusProfileToReturn.setLastName(extUser.getLastName());
                cusProfileToReturn.setMiddleName(extUser.getMiddleName());
                cusProfileToReturn.setMobileNo(extUser.getMobilePhoneNo());
                cusProfileToReturn.setNic(extUser.getNicNumber());
                cusProfileToReturn.setStatusCode("IB200");
                cusProfileToReturn.setStatusDescription("Customer profile returns successfully.");
                cusProfileToReturn.setWalletId(listWalletRequest.getWalletId());
                cusProfileToReturn.setExtErrorCode("");
            } else {
                cusProfileToReturn.setAddressLine1("");
                cusProfileToReturn.setAddressLine2("");
                cusProfileToReturn.setAddressLine3("");
                cusProfileToReturn.setAddressLine4("");
                cusProfileToReturn.setCountryCode("");
                cusProfileToReturn.setEmail("");
                cusProfileToReturn.setFirstName("");
                cusProfileToReturn.setLastName("");
                cusProfileToReturn.setMiddleName("");
                cusProfileToReturn.setMobileNo("");
                cusProfileToReturn.setNic("");
                cusProfileToReturn.setWalletId("");
                cusProfileToReturn.setStatusCode("IB603");
                cusProfileToReturn.setStatusDescription("SYS_ERROR");
                cusProfileToReturn.setFailReason("System error." + extUser.getErrorDesc() + ".");
                cusProfileToReturn.setExtErrorCode("");
            }
        } catch (Exception e) {

            cusProfileToReturn.setAddressLine1("");
            cusProfileToReturn.setAddressLine2("");
            cusProfileToReturn.setAddressLine3("");
            cusProfileToReturn.setAddressLine4("");
            cusProfileToReturn.setCountryCode("");
            cusProfileToReturn.setEmail("");
            cusProfileToReturn.setFirstName("");
            cusProfileToReturn.setLastName("");
            cusProfileToReturn.setMiddleName("");
            cusProfileToReturn.setMobileNo("");
            cusProfileToReturn.setNic("");
            cusProfileToReturn.setWalletId("");
            cusProfileToReturn.setStatusCode("IB603");
            cusProfileToReturn.setStatusDescription("SYS_ERROR");
            cusProfileToReturn.setFailReason("System error." + e.getMessage());
            cusProfileToReturn.setExtErrorCode("");

            log.error("error -" + e);

        }

        log.debug("PostListExternalWalletUserDetails() finished.");

        return cusProfileToReturn;
    }

    private ExternalUserDetail GetExternalUserDataByExternalUserId(String extId, String merchantId) {

        log.info("GetExternalUserDataByExternalUserId() starts.");
        ExternalUserDetail extUserDetail = new ExternalUserDetail();

        try {
            BnkDlUsr bnkDlUsr = userLinkDao.findByUserExtIdAndMerchantId(extId, merchantId);

            extUserDetail.setBankCode(obPCIExtract.EncryptString(bnkDlUsr.getBnkDlUsrPK().getBnkCode()));
            extUserDetail.setPlainBankCode(bnkDlUsr.getBnkDlUsrPK().getBnkCode());
            extUserDetail.setDataEntity1(bnkDlUsr.getDe01());
            extUserDetail.setDataEntity2(bnkDlUsr.getDe02());
            extUserDetail.setDataEntity3(bnkDlUsr.getDe03());
            extUserDetail.setDataEntity4(bnkDlUsr.getDe04());
            extUserDetail.setDataEntity5(bnkDlUsr.getDe05());
            extUserDetail.setMerchantId(bnkDlUsr.getMercId());
            extUserDetail.setRefId(bnkDlUsr.getExtId());
            extUserDetail.setUserId(bnkDlUsr.getBnkDlUsrPK().getUsrId());
            extUserDetail.setUserSuffix(bnkDlUsr.getBnkDlUsrPK().getUsrSfix());
        } catch (Exception e) {

            log.info("GetExternalUserDataByExternalUserId() ends with an error.");
            log.error("Error ", e);
        }
        log.info("GetExternalUserDataByExternalUserId() End");
        return extUserDetail;
    }

    @Override
    public ExternalWalletUserModel ListExternalUserRequest(String userId, String bankCode) {

        log.info("ListExternalUserRequest() Start");
        ExternalWalletUserModel oauthuserlistresponse = new ExternalWalletUserModel();
        try {

            log.info("GetExtrnlUser of iAdmin auth service called for " + userId + " of " + bankCode + " started.");

            UserCommonResAdmin userResponse = iAdminRestClientInapp.getExtrnlUser(
                    bankCode,
                    userId
            );

            log.info("GetExtrnlUser of iAdmin auth service called for " + userId + " of " + bankCode + " finished.");

            if (userResponse != null) {
                if (userResponse.getStaus().equals("00")) {
                    oauthuserlistresponse.setUserId(userResponse.getObjExtrArr().get(0).getUserId());
                    oauthuserlistresponse.setAddressLine1(userResponse.getObjExtrArr().get(0).getAddressLine1());
                    oauthuserlistresponse.setAddressLine2(userResponse.getObjExtrArr().get(0).getAddressLine2());
                    oauthuserlistresponse.setAddressLine3(userResponse.getObjExtrArr().get(0).getAddressLine3());
                    oauthuserlistresponse.setAddressLine4(userResponse.getObjExtrArr().get(0).getAddressLine4());
                    oauthuserlistresponse.setBankCode(userResponse.getObjExtrArr().get(0).getBankCode());
                    oauthuserlistresponse.setEmail(userResponse.getObjExtrArr().get(0).getEmail());
                    oauthuserlistresponse.setErrorDesc("");
                    oauthuserlistresponse.setExtId("");
                    oauthuserlistresponse.setFirstName(userResponse.getObjExtrArr().get(0).getFirstName());
                    oauthuserlistresponse.setLastName(userResponse.getObjExtrArr().get(0).getLastName());
                    oauthuserlistresponse.setMerchantId("");
                    oauthuserlistresponse.setMiddleName(userResponse.getObjExtrArr().get(0).getMiddleName());
                    oauthuserlistresponse.setMobilePhoneNo(userResponse.getObjExtrArr().get(0).getMobilePhoneNo());
                    oauthuserlistresponse.setNicNumber(userResponse.getObjExtrArr().get(0).getNICNumber());
                    oauthuserlistresponse.setSelectedCountryId(userResponse.getObjExtrArr().get(0).getSelectedCountryId());
                } else {
                    oauthuserlistresponse.setErrorDesc(userResponse.getDescription());
                }
            }
        } catch (Exception e) {
            log.info("Error on ListExternalUserRequest() ", e);
        }
        log.info("ListExternalUserRequest() End");
        return oauthuserlistresponse;
    }

}
