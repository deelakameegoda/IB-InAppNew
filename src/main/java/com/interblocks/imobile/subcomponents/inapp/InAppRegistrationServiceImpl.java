package com.interblocks.imobile.subcomponents.inapp;


import com.interblocks.imobile.api.inapp.model.RegisterUserRequest;
import com.interblocks.imobile.api.inapp.model.RegisterUserResponse;
import com.interblocks.iwallet.adaptor.rest.admin.IAdminRestClientInapp;
import com.interblocks.iwallet.adaptor.rest.admin.model.ExtrnlUserRequest2;
import com.interblocks.iwallet.adaptor.rest.admin.model.UserCommonResAdmin;
import com.interblocks.iwallet.model.BnkDlUsr;
import com.interblocks.iwallet.model.BnkDlUsrPK;
import com.interblocks.iwallet.repository.UserLinkRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Log4j2
@Component
public class InAppRegistrationServiceImpl implements InAppRegistrationService {

    @Autowired
    private UserLinkRepository userLinkDao;
    @Autowired
    private IAdminRestClientInapp iAdminRestClientInapp;

    @Override
    public RegisterUserResponse postInsertExternalWalletUser(RegisterUserRequest oNewWalletUser) {

        log.info("-----------------------Recived Request for PostInsertExternalWalletUser() Function---------------------------------");
        log.debug("Setting up the response with dummy values");

        RegisterUserResponse regUserResp = new RegisterUserResponse();
        regUserResp.setException("Insert success.");
        regUserResp.setStatusCode("IB000");
        regUserResp.setStatusDescription("SUCCESS");
        regUserResp.setExtErrorCode("");

        log.debug("Setting up the response with dummy values finished");
        try {

            log.info("Validating inputs started");

            RegisterUserResponse validate = oNewWalletUser.validate();

            if (validate.getStatusCode().equals("IB200")) {

                log.info("Validating inputs finished");

                ExtrnlUserRequest2 walluserdeails = new ExtrnlUserRequest2();

                walluserdeails.setBankCode(oNewWalletUser.getBankCode());
                walluserdeails.setUserId(oNewWalletUser.getUserId());
                walluserdeails.setFirstName(oNewWalletUser.getFirstName());
                walluserdeails.setLastName(oNewWalletUser.getLastName());
                walluserdeails.setMiddleName(oNewWalletUser.getMiddleName());
                walluserdeails.setAddressLine1(oNewWalletUser.getAddressLine1());
                walluserdeails.setAddressLine2(oNewWalletUser.getAddressLine2());
                walluserdeails.setAddressLine3(oNewWalletUser.getAddressLine3());
                walluserdeails.setAddressLine4(oNewWalletUser.getAddressLine4());
                walluserdeails.setEmail(oNewWalletUser.getEmail());
                walluserdeails.setSelectedCountryId(oNewWalletUser.getCountryId());

                walluserdeails.setMobilePhoneNo(oNewWalletUser.getMobilePhoneNo());
                walluserdeails.setNICNumber(oNewWalletUser.getNicNumber());
                walluserdeails.setMerchantId(oNewWalletUser.getMerchantId());

                log.info("iAdmin method extrnlUserRequest is called");

                UserCommonResAdmin respUser = iAdminRestClientInapp.extrnlUserRequest(walluserdeails);

                if (respUser != null) {

                    log.info("Response returned from iAdmin method extrnlUserRequest");
                    log.info("Response status is :" + respUser.getStaus());

                    if (respUser.getStaus().equals("00")) {
                        String extNo = UUID.randomUUID().toString();

                        Date now = new Date();

                        BnkDlUsrPK userLinkPK = new BnkDlUsrPK();
                        userLinkPK.setBnkCode(oNewWalletUser.getBankCode());
                        userLinkPK.setUsrId(oNewWalletUser.getUserId().toUpperCase());
                        userLinkPK.setUsrSfix("IBNK");

                        BnkDlUsr user = new BnkDlUsr();
                        user.setAddBy("System");
                        user.setAddDate(now);
                        user.setExtId(extNo);
                        user.setMercId(oNewWalletUser.getMerchantId());
                        user.setMdfyBy("System");
                        user.setMdfyDate(now);
                        user.setBnkDlUsrPK(userLinkPK);

                        log.info("Saving externel user started");
                        try {
                            userLinkDao.save(user);
                        } catch (Exception e) {
                            log.error("Saving externel user failed");
                            regUserResp.setStatusDescription("Saving externel user failed.");
                            regUserResp.setStatusCode("02");
                            regUserResp.setException("Saving externel user failed.");
                            regUserResp.setFailReason("Saving externel user failed.");

                            return regUserResp;
                        }
                        regUserResp.setStatusDescription(respUser.getDescription());
                        regUserResp.setEmail(oNewWalletUser.getEmail());
                        regUserResp.setFailReason("");
                        regUserResp.setFirstName(oNewWalletUser.getFirstName());
                        regUserResp.setLastName(oNewWalletUser.getLastName());
                        regUserResp.setWalletId(extNo);

                        return regUserResp;

                    } else {

                        log.error("iAdmin service extrnlUserRequest returns the error status of " + respUser.getStaus());

                        regUserResp.setException("Service error.Please contact the administrator.");
                        regUserResp.setStatusCode(respUser.getStaus());
                        regUserResp.setStatusDescription("SYS_ERROR");
                        regUserResp.setFailReason(respUser.getDescription());

                        return regUserResp;
                    }

                    //return validate;
                } else {

                    log.error("iAdmin service extrnlUserRequest returns null");

                    regUserResp.setException("Service error.Please contact the administrator.");
                    regUserResp.setStatusCode("IB603");
                    regUserResp.setStatusDescription("SYS_ERROR");
                    regUserResp.setFailReason("iAdmin service extrnlUserRequest returns null.");

                    return regUserResp;
                }

            } else {
                log.error(validate.getFailReason());

                return validate;
            }
        } catch (Exception e) {

            log.error(e.getMessage(), e);

            regUserResp.setException("Service error.Please contact the administrator.");
            regUserResp.setStatusCode("IB603");
            regUserResp.setStatusDescription("SYS_ERROR");
            regUserResp.setFailReason(e.getMessage());

            return regUserResp;
        }
    }
}
