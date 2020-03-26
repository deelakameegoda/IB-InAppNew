package com.interblocks.imobile.subcomponents.inapp;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.iwallet.adaptor.communicators.card.CardServiceCommunicator;
import com.interblocks.iwallet.adaptor.rest.admin.IAdminRestClientInapp;
import com.interblocks.iwallet.adaptor.rest.admin.model.ExtrnlUserRequest2;
import com.interblocks.iwallet.adaptor.rest.admin.model.UserCommonResAdmin;
import com.interblocks.iwallet.adaptor.rest.client.RestClient;
import com.interblocks.iwallet.model.BnkDlAcct;
import com.interblocks.iwallet.model.BnkDlUsr;
import com.interblocks.iwallet.model.BnkDmAcct;
import com.interblocks.iwallet.oauth.PCIExtract;
import com.interblocks.iwallet.repository.InAppInstrumentRepository;
import com.interblocks.iwallet.util.DBSequenceManager;
import com.interblocks.iwallet.util.DateTimeUtil;
import com.interblocks.webtools.broker.model.card.BalanceInquiryResponse;
import com.interblocks.webtools.broker.model.card.sub.AccountObj;
import com.interblocks.webtools.core.entities.xmladdons.XMLAddons;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Log4j2
@Component
public class InAppCardServiceImpl implements InAppCardService {

    @Autowired
    InAppInstrumentRepository inappInstrumentDao;
    @Autowired
    InAppUserService userManagementBo;
    @Autowired
    DBSequenceManager dbsequencema;
    @Autowired
    RestClient balanceApiRestClient;
    @Autowired
    PCIExtract pciExtract;
    @Autowired
    IAdminRestClientInapp iAdminRestClientInapp;
    @Autowired
    CardServiceCommunicator cardServiceCommunicator;

    @Value("${inapp.otp_expiry_min}")
    private Integer inappOtpExpiryMinutes;
    @Value("${inapp.is_linked_card_to_be_activated}")
    private String isCardNeedToBeActiveBeforeUseStr;

    @Override
    @Transactional
    public AddCardsResponse postInsertCardsToExternalRetailUser(AddCardsRequest insertCardRequest) {
        AddCardsResponse responseToReturn = new AddCardsResponse();
        responseToReturn.setStatusCode("IB200");
        responseToReturn.setStatusDescription("Success.");
        responseToReturn.setFailReason("");
        responseToReturn.setExtErrorCode("");

        responseToReturn.setCardManagementResponseList(new ArrayList<CardManagementResponse>());

        if (insertCardRequest.getWalletId() == null || insertCardRequest.getWalletId().isEmpty()) {
            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("External wallet id cannot be null.");
            responseToReturn.setExtErrorCode("ERR_26_08");
            log.info("External wallet id cannot be null.");

            return responseToReturn;
        }

        log.info("Getting iwallet user external data from db started");

        BnkDlUsr extUserDetails = userManagementBo.getExternalUserData(insertCardRequest.getWalletId(), insertCardRequest.getMerchantId());

        log.info("Getting iwallet user external data from db finished");

        if (extUserDetails.getExtId() == null || extUserDetails.getExtId().isEmpty()) {

            log.error("Requested iwallet user external data cannot be found.");

            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("Requested record cannot be found.");

            return responseToReturn;
        }

        try {
            UserCommonResAdmin extrnlUser = iAdminRestClientInapp.getExtrnlUser(
                    extUserDetails.getBnkDlUsrPK().getBnkCode(),
                    extUserDetails.getBnkDlUsrPK().getUsrId()
            );

            log.info("Getting iwallet user data from iAdmin service method getExtrnlUser finished");

            if (extrnlUser == null || extrnlUser.getStaus().isEmpty() || (!extrnlUser.getStaus().equals("00"))) {

                log.error("Requested iwallet user data cannot be found by iAdmin method getExtrnlUser.");

                responseToReturn.setStatusCode("IB400");
                responseToReturn.setStatusDescription("Bad Request");
                responseToReturn.setFailReason("Requested record cannot be found.");

                return responseToReturn;
            }


            List<CardManagementResponse> cardInsertRespList = new ArrayList<CardManagementResponse>();

            for (Card item : insertCardRequest.getCardList()) {

                int result;
                boolean isCardNeedToBeActiveBeforeUse;

                switch (isCardNeedToBeActiveBeforeUseStr) {
                    case "Yes":
                        isCardNeedToBeActiveBeforeUse = true;
                        break;
                    case "No":
                        isCardNeedToBeActiveBeforeUse = false;
                        break;
                    case "Backend Logic":
                        isCardNeedToBeActiveBeforeUse = this.isCardNeedToBeActiveBeforeUse(item.getCardNumber());
                        break;
                    default:
                        isCardNeedToBeActiveBeforeUse = false;
                        break;
                }

                Date otpExpTime = DateTimeUtil.addMinutes(new Date(), inappOtpExpiryMinutes);
                BnkDmAcct cardData = new BnkDmAcct();

                int index;

                if (!(item.getIndex() == null || item.getIndex().isEmpty())) {
                    try {
                        index = Integer.parseInt(item.getIndex());
                    } catch (Exception e) {

                        index = -1;
                    }
                } else {
                    index = -1;
                }

                ExtrnlUserRequest2 relatedExternalUser = extrnlUser.getObjExtrArr().get(0);

                if (item.getCardHolderName() == null || item.getCardHolderName().isEmpty()) {
                    item.setCardHolderName(relatedExternalUser.getFirstName());
                }

                Date now = new Date();

                result = this.insertCardToRetailUser(extUserDetails.getBnkDlUsrPK().getUsrSfix(),
                        item.getCardNumber(), "1", "OCP-" + item.getCardType(), Integer.toString(extrnlUser.getUserSrl()),
                        now, DateTimeUtil.addYears(now, 250), "0", "0", "System",
                        now, "System", now, extUserDetails.getBnkDlUsrPK().getUsrId(),
                        extUserDetails.getBnkDlUsrPK().getBnkCode(),
                        item.isDefault(), item.getCardHolderName(), item.getExpMonth(),
                        item.getExpYear(), isCardNeedToBeActiveBeforeUse, otpExpTime,
                        item.getMaskedCardNumber(), index, item.getActivationOTP(), item.getCardRef(),
                        false, item.getCardBin());

                if (result == -1) {
                    CardManagementResponse cardInsertRespErr = new CardManagementResponse();

                    cardInsertRespErr.setCardNo(item.getCardNumber());
                    cardInsertRespErr.setCardRef("");
                    cardInsertRespErr.setCardType(item.getCardType());
                    cardInsertRespErr.setExepation("Error in card insert.");
                    cardInsertRespErr.setIsDefault(item.isDefault());
                    cardInsertRespErr.setStatusCode("IB603");
                    cardInsertRespErr.setStatusDescription("ERROR");
                    cardInsertRespErr.setFailReason("Error in card insert.");
                    cardInsertRespErr.setMaskedCardNumber(item.getMaskedCardNumber());
                    cardInsertRespErr.setIndex(Integer.toString(index));
                    cardInsertRespErr.setIsExpired(false);
                    cardInsertRespErr.setCardBin(item.getCardBin());

                    cardInsertRespList.add(cardInsertRespErr);

                    log.error("User Added with an exception", new Exception("Card adding failed."));

                    responseToReturn.setCardManagementResponseList(cardInsertRespList);
                    responseToReturn.setWalletId(extUserDetails.getExtId());

                    return responseToReturn;

                } else {
                    log.info("Getting details of inserted card started");
                    cardData = inappInstrumentDao.findByRef(new BigDecimal(result));
                    log.info("Getting details of inserted card started");
                }

                CardManagementResponse cardInsertResp = new CardManagementResponse();

                cardInsertResp.setCardNo(item.getCardNumber());
                cardInsertResp.setCardRef(cardData.getBnkDlAcct().getExtId());
                cardInsertResp.setCardType(item.getCardType());
                cardInsertResp.setExepation("");
                cardInsertResp.setIsDefault(item.isDefault());
                cardInsertResp.setStatusCode("IB200");
                cardInsertResp.setStatusDescription("SUCCESS");
                cardInsertResp.setFailReason("");
                cardInsertResp.setMaskedCardNumber(item.getMaskedCardNumber());
                cardInsertResp.setIndex(cardData.getBnkDlAcct().getDe03());
                cardInsertResp.setIsExpired(isExpired(cardData.getBnkDlAcct().getExpYear(), cardData.getBnkDlAcct().getExpMnth()));
                cardInsertResp.setCardHolderName(item.getCardHolderName());
                cardInsertResp.setCardBin(cardData.getBnkDlAcct().getCrdBin());

                if (isCardNeedToBeActiveBeforeUse) {
                    cardInsertResp.setActivationOTP(cardData.getBnkDlAcct().getDe01());
                    cardInsertResp.setIsActive(false);
                } else {
                    cardInsertResp.setActivationOTP("");
                    cardInsertResp.setIsActive(true);
                }

                cardInsertRespList.add(cardInsertResp);
            }

            responseToReturn.setCardManagementResponseList(cardInsertRespList);
            responseToReturn.setWalletId(extUserDetails.getExtId());

        } catch (Exception e) {
            log.error("Unexpected error", e);

            responseToReturn.setStatusCode("IB603");
            responseToReturn.setStatusDescription("System Error.");
            responseToReturn.setFailReason("Unexpected error" + e.getMessage());

        }

        return responseToReturn;
    }

    @Override
    @Transactional
    public int insertCardToRetailUser(String comid, String accountid,
                                      String status,
                                      String actype,
                                      String customerid, Date acc_accessfromdate,
                                      Date acc_accesstodate,
                                      String dailylimit,
                                      String monthlylimit, String addedby,
                                      Date addeddate,
                                      String modifyby,
                                      Date modifydate, String userid,
                                      String bankCode,
                                      boolean isDefault, String cardHolderName,
                                      String expMonth, String expYear,
                                      boolean isActivationRequired, Date otpExpTime,
                                      String maskedCardNo,
                                      int index, String activationOTP,
                                      String cardRef, boolean isSystemGenerated, String cardBin
    ) {

        log.info("insertCardToRetailUser started");

        try {
            log.info("Checking whether the account/card already added to the user started");

            BnkDmAcct relevantAccountInfo = inappInstrumentDao.findByUserAndCard(userid, comid, bankCode, accountid);

            log.info("Checking whether the account/card already added to the user finished");

            if (relevantAccountInfo != null) {

                log.info("The account/card already added to the user");

                boolean isCardToBeActivated = false;

                if (isActivationRequired && !(relevantAccountInfo.getBnkDlAcct().getDe02().equals("Y"))) {
                    log.info("The account/card should be activated but still not activated");

                    isCardToBeActivated = true;
                }

                log.info("Updating the account/card with new data");
                relevantAccountInfo.setStatCode(BigInteger.valueOf(3));
                relevantAccountInfo.setAcctAcssFromDate(acc_accessfromdate);
                relevantAccountInfo.setAcctAcssToDate(acc_accesstodate);
                relevantAccountInfo.setDlyLmt(Long.parseLong(dailylimit));
                relevantAccountInfo.setDlyLmt(Long.parseLong(monthlylimit));
                relevantAccountInfo.setExctdDate(addeddate);
                relevantAccountInfo.setDlyShaLmt(dailylimit);
                relevantAccountInfo.setMnthlyShaLmt(monthlylimit);
                relevantAccountInfo.setAcctTyp(actype);

                if (isCardToBeActivated) {

                    log.debug("Card need to be activated before use");

                    if (activationOTP == null || (activationOTP.isEmpty())) {

                        log.debug("Given activation OTP is null or empty");

                        log.debug("Generating a new activation OTP...");

                        int n = 100000 + new Random().nextInt(900000);
                        activationOTP = Integer.toString(n);

                        log.debug("Generating a new activation OTP finished");
                    }

                }

                relevantAccountInfo.getBnkDlAcct().setIsDef(isDefault ? "Y" : "N");
                relevantAccountInfo.getBnkDlAcct().setMdfyBy(modifyby);
                relevantAccountInfo.getBnkDlAcct().setMdfyDate(modifydate);
                relevantAccountInfo.getBnkDlAcct().setCrdHldrName(cardHolderName);
                relevantAccountInfo.getBnkDlAcct().setExpMnth(expMonth);
                relevantAccountInfo.getBnkDlAcct().setExpYear(expYear);

                if (isCardToBeActivated) {
                    relevantAccountInfo.getBnkDlAcct().setDe01(activationOTP);
                }
                relevantAccountInfo.getBnkDlAcct().setOtpExpTime(otpExpTime);
                relevantAccountInfo.getBnkDlAcct().setDe03(Integer.toString(index));
                relevantAccountInfo.getBnkDlAcct().setDe04(maskedCardNo);


                try {
                    inappInstrumentDao.save(relevantAccountInfo);
                    log.info("Updating the account/card with new data success");

                    // send otp using iAlert
                    log.info("OTP for card " + accountid + " is : " + activationOTP);
                    return relevantAccountInfo.getRefId().intValue();
                    // send otp finished

                } catch (Exception e) {

                    log.error("Error updating the existing record. Id : " + relevantAccountInfo.getRefId());
                    return 0;
                }
            } else {

                int existingHighestIndex = -1;

                log.info("Getting user card list for get the maximum index exists");

                List<BnkDmAcct> existingUserCardList = inappInstrumentDao.findByUser(userid, comid, bankCode);

                log.info("Getting user card list for get the maximum index exists finished");

                if (existingUserCardList != null && existingUserCardList.size() > 0) {
                    log.info(existingUserCardList.size() + " user cards returned");

                    for (BnkDmAcct bnkDmAccounts : existingUserCardList) {
                        if (Integer.parseInt(bnkDmAccounts.getBnkDlAcct().getDe03()) > existingHighestIndex) {
                            existingHighestIndex = Integer.parseInt(bnkDmAccounts.getBnkDlAcct().getDe03());
                        }
                    }
                }

                log.info("existingHighestIndex=" + existingHighestIndex);

                if (index < 0) {

                    log.info("Received index is the default index. Make index is next highest");

                    index = existingHighestIndex + 1;
                } else {

                    log.info("Received index is not the default index. Seaching whether the received index is already exist in the DB");

                    for (BnkDmAcct bnkDmAccounts : existingUserCardList) {
                        {
                            if (Integer.parseInt(bnkDmAccounts.getBnkDlAcct().getDe03()) == index) {

                                log.info("Received index is already exist in the DB. Make index is next highest");

                                index = existingHighestIndex + 1;
                            }
                        }
                    }

                }

                BnkDmAcct newInstrument = new BnkDmAcct();

                log.debug("Starting Get id from SEQ_WLT_DM_USER_ACCOUNTS " + userid);
                long idNextVal = dbsequencema.getNextValue("SEQ_WLT_DM_USER_ACCOUNTS");
                log.debug("End SEQ_WLT_DM_USER_ACCOUNTS id " + idNextVal);

                newInstrument.setCoId(comid);
                newInstrument.setAcctNo(accountid);
                newInstrument.setStatCode(new BigInteger(status));
                newInstrument.setAcctTyp(actype);
                newInstrument.setCoreBnkRefNo(customerid);
                newInstrument.setAcctAcssFromDate(acc_accessfromdate);
                newInstrument.setAcctAcssToDate(acc_accesstodate);
                newInstrument.setDlyLmt(Long.parseLong(dailylimit));
                newInstrument.setMnthlyLmt(Long.parseLong(monthlylimit));
                newInstrument.setAddBy(addedby);
                newInstrument.setAddDate(addeddate);
                newInstrument.setMdfyBy(modifyby);
                newInstrument.setMdfyDate(modifydate);
                newInstrument.setUsrId(userid.toUpperCase());
                newInstrument.setExctdDate(addeddate);
                newInstrument.setDlyShaLmt(dailylimit);
                newInstrument.setMnthlyShaLmt(monthlylimit);
                newInstrument.setBnkCode(bankCode);

                if (activationOTP == null || (activationOTP.isEmpty())) {

                    log.debug("Given activation OTP is null or empty");

                    log.debug("Generating a new activation OTP...");

                    int n = 100000 + new Random().nextInt(900000);
                    activationOTP = Integer.toString(n);

                    log.debug("Generating a new activation OTP finished");
                }

                BnkDlAcct newInstrumentLink = new BnkDlAcct();
                newInstrumentLink.setRefId(new BigDecimal(idNextVal));
                newInstrumentLink.setExtId(cardRef);
                newInstrumentLink.setIsDef(isDefault ? "Y" : "N");
                newInstrumentLink.setAddBy(addedby);
                newInstrumentLink.setAddDate(addeddate);
                newInstrumentLink.setMdfyBy(modifyby);
                newInstrumentLink.setMdfyDate(modifydate);
                newInstrumentLink.setCrdHldrName(cardHolderName);
                newInstrumentLink.setExpMnth(expMonth);
                newInstrumentLink.setExpYear(expYear);
                if (isActivationRequired) {
                    newInstrumentLink.setDe01(activationOTP);
                    newInstrumentLink.setDe02("N");
                    newInstrumentLink.setOtpExpTime(otpExpTime);
                } else {
                    newInstrumentLink.setDe02("Y");
                }
                newInstrumentLink.setDe03(Integer.toString(index));
                newInstrumentLink.setDe04(maskedCardNo);
                newInstrumentLink.setDe05(isSystemGenerated ? "Y" : "N");
                newInstrumentLink.setCrdBin(cardBin);
                newInstrumentLink.setBnkDmAcct(newInstrument);

                newInstrument.setBnkDlAcct(newInstrumentLink);

                log.info("Inserting new instrument");

                newInstrument.setRefId(new BigDecimal(idNextVal));

                //int insertResult =


                try {
                    inappInstrumentDao.save(newInstrument);

                    log.info("Inserting new finished");

                    log.info("Inserting new instrument success");

                    log.info("Fetching the new instrument from the DB");
                    BnkDmAcct insertedInstrument = inappInstrumentDao.findByUserAndCard(userid, comid, bankCode, accountid);
                    log.info("Fetching the new instrument from the DB finished");

                    return insertedInstrument.getRefId().intValue();
                } catch (Exception e) {
                    log.error("Inserting new instrument failed");
                    return -1;
                }
            }
        } catch (Exception e) {
            log.error("insertCardToRetailUser failed");
            log.error(e.getMessage(), e);
            return 0;
        }

    }

    private boolean isCardNeedToBeActiveBeforeUse(String cardNumber) {
        log.info("Geting card details from view BNK_V0_CARDS_WITH_NEED_TO_ACTIVE_STATUS");
        List<Object[]> cardActivationDetails = inappInstrumentDao.isCardNeedToBeActiveBeforeUse(cardNumber);
        log.info("Geting card details from view BNK_V0_CARDS_WITH_NEED_TO_ACTIVE_STATUS finished");

        boolean isCardNeedToBeActiveBeforeUse = false;

        for (Object[] objmercDetails : cardActivationDetails) {

            isCardNeedToBeActiveBeforeUse = objmercDetails[0].toString().toLowerCase().equals("on");

        }

        return isCardNeedToBeActiveBeforeUse;
    }

    private boolean isExpired(String expYear, String expMonth) {

        if (Integer.parseInt(expYear) < Calendar.YEAR) {
            return false;
        } else if (Integer.parseInt(expYear) == Calendar.YEAR) {
            return Integer.parseInt(expMonth) < Calendar.MONTH;
        } else {
            return true;
        }

    }

    @Override
    public ListExternalCardResponse postListAllUserCardsWithDeleted(ListExternalCardRequest listExternalCardRequest) {
        return getCardList(listExternalCardRequest, true);
    }

    @Override
    public ListExternalCardResponse postListAllUserCards(ListExternalCardRequest listExternalCardRequest) {
        return getCardList(listExternalCardRequest, false);
    }

    private ListExternalCardResponse getCardList(ListExternalCardRequest listExternalCardRequest, boolean includeDeleted) {
        ListExternalCardResponse listExternalCardResponse = new ListExternalCardResponse();
        listExternalCardResponse.setExtErrorCode("");

        try {

            if (listExternalCardRequest.getWalletId() == null || listExternalCardRequest.getWalletId().isEmpty()) {
                listExternalCardResponse.setStatusCode("IB400");
                listExternalCardResponse.setStatusDescription("Bad Request");
                listExternalCardResponse.setFailReason("External wallet id cannot be null.");
                listExternalCardResponse.setExtErrorCode("ERR_26_10");

                log.info("External wallet id cannot be null.");

                return listExternalCardResponse;
            }

            log.debug("getExternalUserData of UserManagementBo called.");

            BnkDlUsr extUserDetails = userManagementBo.getExternalUserData(listExternalCardRequest.getWalletId(), listExternalCardRequest.getMerchantId());

            log.debug("getExternalUserData of UserManagementBo finished.");

            if (extUserDetails.getExtId() == null || extUserDetails.getExtId().isEmpty()) {
                listExternalCardResponse.setStatusCode("IB400");
                listExternalCardResponse.setStatusDescription("Bad Request");
                listExternalCardResponse.setFailReason("Requested record cannot be found.");

                log.info("Requested record cannot be found.");
                return listExternalCardResponse;
            }

            List<BnkDmAcct> existingUserCardList;
            if (includeDeleted) {
                log.info("Getting user card list with deleted ");
                existingUserCardList = inappInstrumentDao.findByUserWithDeletedCard(extUserDetails.getBnkDlUsrPK().getUsrId(), extUserDetails.getBnkDlUsrPK().getUsrSfix(), extUserDetails.getBnkDlUsrPK().getBnkCode());
                log.info("Getting user card list with deleted finished");
            } else {
                log.info("Getting user card list ");
                existingUserCardList = inappInstrumentDao.findByUser(extUserDetails.getBnkDlUsrPK().getUsrId(), extUserDetails.getBnkDlUsrPK().getUsrSfix(), extUserDetails.getBnkDlUsrPK().getBnkCode());
                log.info("Getting user card list finished");
            }


            List<Card> cardListResponse = new ArrayList<Card>();

            for (BnkDmAcct item : existingUserCardList) {
                Card crd = new Card();
                crd.setCardHolderName(item.getBnkDlAcct().getCrdHldrName());
                crd.setCardNumber(item.getAcctNo());
                crd.setCardType(item.getAcctTyp().startsWith("OCP") ? item.getAcctTyp().split("-")[1] : item.getAcctTyp());
                crd.setExpMonth(item.getBnkDlAcct().getExpMnth());
                crd.setExpYear(item.getBnkDlAcct().getExpYear());
                crd.setDefault(item.getBnkDlAcct().getIsDef().equals("Y"));
                crd.setCardRef(item.getBnkDlAcct().getExtId());
                crd.setActive(item.getBnkDlAcct().getDe02().equals("Y"));
                crd.setIndex(item.getBnkDlAcct().getDe03());
                crd.setMaskedCardNumber(item.getBnkDlAcct().getDe04());
                crd.setExpired(isExpired(item.getBnkDlAcct().getExpYear(), item.getBnkDlAcct().getExpMnth()));
                crd.setCardBin(item.getBnkDlAcct().getCrdBin());
                crd.setStatus(item.getStatCode().toString());

                cardListResponse.add(crd);
            }

            listExternalCardResponse.setCardList(cardListResponse);
            listExternalCardResponse.setFailReason("");
            listExternalCardResponse.setStatusCode("IB200");
            listExternalCardResponse.setStatusDescription("Card list returns successfully.");
            listExternalCardResponse.setWalletId(listExternalCardRequest.getWalletId());
        } catch (Exception e) {
            String errMsg = e.getMessage();
            log.error(errMsg);
            log.error(e.getMessage(), e);

            listExternalCardResponse.setCardList(new ArrayList<Card>());
            listExternalCardResponse.setWalletId("");
            listExternalCardResponse.setStatusCode("IB603");
            listExternalCardResponse.setStatusDescription("SYS_ERROR");
            listExternalCardResponse.setFailReason("System error." + errMsg);
        }

        return listExternalCardResponse;
    }

    @Override
    public EditExternalCardsResponse postEditCards(EditExternalCardsRequest editCardRequest) {

        EditExternalCardsResponse responseToReturn = new EditExternalCardsResponse();
        responseToReturn.setStatusCode("IB200");
        responseToReturn.setStatusDescription("Success.");
        responseToReturn.setFailReason("");
        responseToReturn.setExtErrorCode("");
        responseToReturn.setCardManagementResponseList(new ArrayList<CardManagementResponse>());

        if (editCardRequest.getWalletId() == null || editCardRequest.getWalletId().isEmpty()) {

            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("External wallet id cannot be null.");
            responseToReturn.setExtErrorCode("ERR_26_12");

            log.error("External wallet id cannot be null.");

            return responseToReturn;
        }
        if (editCardRequest.getMerchantId() == null || editCardRequest.getMerchantId().isEmpty()) {
            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("Merchant id and card list cannot be null.");
            responseToReturn.setExtErrorCode("ERR_26_13");

            log.error("Merchant id and card list cannot be null.");

            return responseToReturn;
        }
        if (editCardRequest.getCardList() == null) {
            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("Card list cannot be null.");
            responseToReturn.setExtErrorCode("ERR_27_21");

            log.error("Card list cannot be null.");

            return responseToReturn;
        }

        log.debug("getExternalUserData of UserManagementBo called.");

        BnkDlUsr extUserDetails = userManagementBo.getExternalUserData(editCardRequest.getWalletId(), editCardRequest.getMerchantId());

        log.debug("getExternalUserData of UserManagementBo finished.");


        try {
            UserCommonResAdmin extrnlUser = iAdminRestClientInapp.getExtrnlUser(
                    extUserDetails.getBnkDlUsrPK().getBnkCode(),
                    extUserDetails.getBnkDlUsrPK().getUsrId()
            );

            log.info("Getting iwallet user data from iAdmin service method getExtrnlUser finished");

            if (extrnlUser == null || extrnlUser.getStaus().isEmpty() || (!extrnlUser.getStaus().equals("00"))) {

                log.error("Requested iwallet user data cannot be found by iAdmin method getExtrnlUser.");

                responseToReturn.setStatusCode("IB400");
                responseToReturn.setStatusDescription("Bad Request");
                responseToReturn.setFailReason("Requested record cannot be found.");

                return responseToReturn;
            }

            if (extUserDetails.getExtId() == null || extUserDetails.getExtId().isEmpty()) {
                responseToReturn.setStatusCode("IB400");
                responseToReturn.setStatusDescription("Bad Request");
                responseToReturn.setFailReason("Requested record cannot be found.");

                log.info("Requested record cannot be found.");
                return responseToReturn;
            }


            List<CardManagementResponse> cardInsertRespList = new ArrayList<CardManagementResponse>();

            for (Card card : editCardRequest.getCardList()) {

                int index;

                if (!(card.getIndex() == null || card.getIndex().isEmpty())) {
                    try {
                        index = Integer.parseInt(card.getIndex());
                    } catch (Exception e) {

                        index = -1;
                    }
                } else {
                    index = -1;
                }

                ExtrnlUserRequest2 relatedExternalUser = extrnlUser.getObjExtrArr().get(0);

                if (card.getCardHolderName() == null || card.getCardHolderName().isEmpty()) {
                    card.setCardHolderName(relatedExternalUser.getFirstName());
                }

                Date now = new Date();
                Date otpExpTime = DateTimeUtil.addMinutes(now, inappOtpExpiryMinutes);
                BnkDmAcct cardData = new BnkDmAcct();

                int result = this.insertCardToRetailUser(
                        extUserDetails.getBnkDlUsrPK().getUsrSfix(),
                        card.getCardNumber(),
                        "1",
                        "OCP-" + card.getCardType(),
                        Integer.toString(extrnlUser.getUserSrl()),
                        now,
                        DateTimeUtil.addYears(now, 250),
                        "0",
                        "0",
                        extUserDetails.getBnkDlUsrPK().getUsrId(),
                        now,
                        extUserDetails.getBnkDlUsrPK().getUsrId(),
                        now,
                        extUserDetails.getBnkDlUsrPK().getUsrId(),
                        extUserDetails.getBnkDlUsrPK().getBnkCode(),
                        card.isDefault(),
                        card.getCardHolderName(),
                        card.getExpMonth(),
                        card.getExpYear(),
                        false,
                        otpExpTime,
                        card.getMaskedCardNumber(),
                        index,
                        card.getActivationOTP(),
                        card.getCardRef(),
                        false,
                        card.getCardBin());

                if (result == -1) {

                    CardManagementResponse cardInsertRespErr = new CardManagementResponse();

                    cardInsertRespErr.setCardNo(card.getCardNumber());
                    cardInsertRespErr.setCardRef("");
                    cardInsertRespErr.setCardType(card.getCardType());
                    cardInsertRespErr.setExepation("Error in card insert.");
                    cardInsertRespErr.setIsDefault(card.isDefault());
                    cardInsertRespErr.setStatusCode("IB603");
                    cardInsertRespErr.setStatusDescription("ERROR");
                    cardInsertRespErr.setFailReason("Error in card insert.");
                    cardInsertRespErr.setMaskedCardNumber(card.getMaskedCardNumber());
                    cardInsertRespErr.setIndex(Integer.toString(index));
                    cardInsertRespErr.setIsExpired(false);
                    cardInsertRespErr.setCardBin(card.getCardBin());

                    cardInsertRespList.add(cardInsertRespErr);

                    log.error("User Added with an exception", new Exception("Card adding failed."));

                    responseToReturn.setCardManagementResponseList(cardInsertRespList);
                    responseToReturn.setWalletId(extUserDetails.getExtId());

                    return responseToReturn;
                } else {
                    log.info("Getting details of inserted card started");
                    cardData = inappInstrumentDao.findByRef(new BigDecimal(result));
                    log.info("Getting details of inserted card started");
                }

                CardManagementResponse cardInsertResp = new CardManagementResponse();

                cardInsertResp.setCardNo(card.getCardNumber());
                cardInsertResp.setCardRef(cardData.getBnkDlAcct().getExtId());
                cardInsertResp.setCardType(card.getCardType());
                cardInsertResp.setExepation("");
                cardInsertResp.setIsDefault(card.isDefault());
                cardInsertResp.setStatusCode("IB200");
                cardInsertResp.setStatusDescription("SUCCESS");
                cardInsertResp.setFailReason("");
                cardInsertResp.setMaskedCardNumber(card.getMaskedCardNumber());
                cardInsertResp.setIndex(cardData.getBnkDlAcct().getDe03());
                cardInsertResp.setIsExpired(isExpired(cardData.getBnkDlAcct().getExpYear(), cardData.getBnkDlAcct().getExpMnth()));
                cardInsertResp.setCardHolderName(card.getCardHolderName());
                cardInsertResp.setCardBin(cardData.getBnkDlAcct().getCrdBin());

                cardInsertResp.setActivationOTP("");
                cardInsertResp.setIsActive(true);

                cardInsertRespList.add(cardInsertResp);
            }

            responseToReturn.setCardManagementResponseList(cardInsertRespList);
            responseToReturn.setWalletId(extUserDetails.getExtId());
        } catch (Exception e) {

            String errMessage = e.getMessage();

            log.error(errMessage);
            log.error(e.getMessage(), e);

            responseToReturn.setStatusCode("IB603");
            responseToReturn.setStatusDescription("System Error.");
            responseToReturn.setFailReason(errMessage);

        }

        return responseToReturn;
    }

    @Override
    public DeleteExternalCardResponse postDeleteCards(DeleteExternalCardRequest deleteCardRequest) {
        DeleteExternalCardResponse responseToReturn = new DeleteExternalCardResponse();
        responseToReturn.setStatusCode("IB200");
        responseToReturn.setStatusDescription("Success.");
        responseToReturn.setFailReason("");
        responseToReturn.setExtErrorCode("");
        responseToReturn.setCardManagementResponse(new CardManagementResponse());

        if (deleteCardRequest.getWalletId() == null || deleteCardRequest.getWalletId().isEmpty()) {
            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("External wallet id cannot be null.");
            responseToReturn.setExtErrorCode("ERR_26_14");

            log.error("External wallet id cannot be null.");

            return responseToReturn;
        }
        if (deleteCardRequest.getMerchantId() == null || deleteCardRequest.getMerchantId().isEmpty()) {
            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("Merchant id cannot be null.");
            responseToReturn.setExtErrorCode("ERR_26_15");

            log.error("Merchant id cannot be null.");

            return responseToReturn;
        }
        if (deleteCardRequest.getCardRef() == null || deleteCardRequest.getCardRef().isEmpty()) {
            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("Card ref id cannot be null.");
            responseToReturn.setExtErrorCode("ERR_27_14");

            log.error("Card ref id cannot be null.");

            return responseToReturn;
        }

        log.debug("getExternalUserData of UserManagementBo called.");

        BnkDlUsr extUserDetails = userManagementBo.getExternalUserData(deleteCardRequest.getWalletId(), deleteCardRequest.getMerchantId());

        log.debug("getExternalUserData of UserManagementBo finished.");

        try {
            UserCommonResAdmin extrnlUser = iAdminRestClientInapp.getExtrnlUser(
                    extUserDetails.getBnkDlUsrPK().getBnkCode(),
                    extUserDetails.getBnkDlUsrPK().getUsrId()
            );

            log.info("Getting iwallet user data from iAdmin service method getExtrnlUser finished");

            if (extrnlUser == null || extrnlUser.getStaus().isEmpty() || (!extrnlUser.getStaus().equals("00"))) {

                log.error("Requested iwallet user data cannot be found by iAdmin method getExtrnlUser.");

                responseToReturn.setStatusCode("IB400");
                responseToReturn.setStatusDescription("Bad Request");
                responseToReturn.setFailReason("Requested record cannot be found.");

                return responseToReturn;
            }

            if (extUserDetails.getExtId() == null || extUserDetails.getExtId().isEmpty()) {
                responseToReturn.setStatusCode("IB400");
                responseToReturn.setStatusDescription("Bad Request");
                responseToReturn.setFailReason("Requested record cannot be found.");

                log.error("Requested record cannot be found.");
                return responseToReturn;
            }


            CardManagementResponse cardDelResp = new CardManagementResponse();

            BnkDmAcct cardToDelete = inappInstrumentDao.findByExtId(deleteCardRequest.getCardRef());

            if (cardToDelete == null) {
                responseToReturn.setStatusCode("IB400");
                responseToReturn.setStatusDescription("Bad Request");
                responseToReturn.setFailReason("Requested record cannot be found.");

                log.error("Requested record cannot be found.");

                return responseToReturn;
            }

            log.info("Deleting the card by setting the status code = 99.");

            cardToDelete.setStatCode(BigInteger.valueOf(99));

            try {
                inappInstrumentDao.save(cardToDelete);

                log.info("Deleting the card by setting the status code = 99 finished.");
                log.error("Deleting the card by setting the status code = 99 successful.");

                cardDelResp.setCardNo(cardToDelete.getAcctNo());
                cardDelResp.setCardRef(deleteCardRequest.getCardRef());
                cardDelResp.setCardType(cardToDelete.getAcctTyp().startsWith("OCP") ? cardToDelete.getAcctTyp().split("-")[1] : cardToDelete.getAcctTyp());
                cardDelResp.setIsDefault(cardToDelete.getBnkDlAcct().getIsDef().equals("Y"));
                cardDelResp.setIsActive(cardToDelete.getBnkDlAcct().getDe02().equals("Y"));
                cardDelResp.setMaskedCardNumber(cardToDelete.getBnkDlAcct().getDe04());
                cardDelResp.setCardBin(cardToDelete.getBnkDlAcct().getCrdBin());
                cardDelResp.setStatusCode("IB200");
                cardDelResp.setStatusDescription("SUCCESS");
                cardDelResp.setFailReason("");

                responseToReturn.setCardManagementResponse(cardDelResp);

                responseToReturn.setWalletId(deleteCardRequest.getWalletId());
                responseToReturn.setStatusCode("IB200");
                responseToReturn.setStatusDescription("SUCCESS");
                responseToReturn.setFailReason("");

                return responseToReturn;
            } catch (Exception e) {

                log.error("Deleting the card by setting the status code = 99 failed.", e);

                cardDelResp.setCardNo(cardToDelete.getAcctNo());
                cardDelResp.setCardRef(deleteCardRequest.getCardRef());
                cardDelResp.setCardType(cardToDelete.getAcctTyp().startsWith("OCP") ? cardToDelete.getAcctTyp().split("-")[1] : cardToDelete.getAcctTyp());
                cardDelResp.setIsDefault(cardToDelete.getBnkDlAcct().getIsDef().equals("Y"));
                cardDelResp.setIsActive(cardToDelete.getBnkDlAcct().getDe02().equals("Y"));
                cardDelResp.setCardBin(cardToDelete.getBnkDlAcct().getCrdBin());
                cardDelResp.setStatusCode("IB603");
                cardDelResp.setStatusDescription("SYS_ERROR");
                cardDelResp.setFailReason("System error.");

                responseToReturn.setCardManagementResponse(cardDelResp);

                responseToReturn.setWalletId(deleteCardRequest.getWalletId());
                responseToReturn.setStatusCode("IB603");
                responseToReturn.setStatusDescription("SYS_ERROR");
                responseToReturn.setFailReason("System error.");

                return responseToReturn;
            }


        } catch (Exception e) {
            String errMessage = e.getMessage();

            log.error(errMessage);
            log.error(e.getMessage(), e);

            responseToReturn.setStatusCode("IB603");
            responseToReturn.setStatusDescription("System Error.");
            responseToReturn.setFailReason(errMessage);

            return responseToReturn;
        }

    }

    @Override
    public ActivateExternalCardResponse postActivateCards(ActivateExternalCardRequest activateCardRequest) {
        ActivateExternalCardResponse responseToReturn = new ActivateExternalCardResponse();

        responseToReturn.setStatusCode("IB200");
        responseToReturn.setStatusDescription("Success.");
        responseToReturn.setFailReason("");
        responseToReturn.setExtErrorCode("");
        responseToReturn.setCardManagementResponse(new CardManagementResponse());

        if (activateCardRequest.getWalletId() == null || activateCardRequest.getWalletId().isEmpty()) {

            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("External wallet id cannot be null.");
            responseToReturn.setExtErrorCode("ERR_26_16");

            log.error("External wallet id cannot be null.");

            return responseToReturn;
        }
        if (activateCardRequest.getMerchantId() == null || activateCardRequest.getMerchantId().isEmpty()) {

            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("Merchant id cannot be null.");
            responseToReturn.setExtErrorCode("ERR_26_17");

            log.error("Merchant id cannot be null.");

            return responseToReturn;
        }
        if (activateCardRequest.getCardRef() == null || activateCardRequest.getCardRef().isEmpty()) {

            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("Card ref id cannot be null.");
            responseToReturn.setExtErrorCode("ERR_26_14");

            log.error("Card ref id cannot be null.");

            return responseToReturn;
        }

        log.debug("getExternalUserData of UserManagementBo called.");

        BnkDlUsr extUserDetails = userManagementBo.getExternalUserData(activateCardRequest.getWalletId(), activateCardRequest.getMerchantId());

        log.debug("getExternalUserData of UserManagementBo finished.");

        try {
            UserCommonResAdmin extrnlUser = iAdminRestClientInapp.getExtrnlUser(
                    extUserDetails.getBnkDlUsrPK().getBnkCode(),
                    extUserDetails.getBnkDlUsrPK().getUsrId()

            );

            log.info("Getting iwallet user data from iAdmin service method getExtrnlUser finished");

            if (extrnlUser == null || extrnlUser.getStaus().isEmpty() || (!extrnlUser.getStaus().equals("00"))) {

                log.error("Requested iwallet user data cannot be found by iAdmin method getExtrnlUser.");

                responseToReturn.setStatusCode("IB400");
                responseToReturn.setStatusDescription("Bad Request");
                responseToReturn.setFailReason("Requested record cannot be found.");

                return responseToReturn;
            }

            if (extUserDetails.getExtId() == null || extUserDetails.getExtId().isEmpty()) {
                responseToReturn.setStatusCode("IB400");
                responseToReturn.setStatusDescription("Bad Request");
                responseToReturn.setFailReason("Requested record cannot be found.");

                log.error("Requested record cannot be found.");
                return responseToReturn;
            }

            CardManagementResponse cardActivateResp = new CardManagementResponse();
            cardActivateResp.setExtErrorCode("");

            log.info("Getting the card by ref");

            BnkDmAcct cardToActive = inappInstrumentDao.findByExtId(activateCardRequest.getCardRef());

            log.info("Getting the card by ref finished");

            if (cardToActive == null || BigDecimal.ZERO.equals(cardToActive.getRefId())) {

                //cardActivateResp.setCardNo(cardToActive.getAcctNo());
                cardActivateResp.setCardRef(activateCardRequest.getCardRef());
                if (cardToActive != null) {
                    cardActivateResp.setCardType(cardToActive.getAcctTyp().startsWith("OCP") ? cardToActive.getAcctTyp().split("-")[1] : cardToActive.getAcctTyp());
                    cardActivateResp.setIsDefault(cardToActive.getBnkDlAcct().getIsDef().equals("Y"));
                    cardActivateResp.setIsActive(cardToActive.getBnkDlAcct().getDe02().equals("Y"));
                    cardActivateResp.setCardBin(cardToActive.getBnkDlAcct().getCrdBin());
                }

                cardActivateResp.setStatusCode("IB400");
                cardActivateResp.setStatusDescription("SYS_ERROR");
                cardActivateResp.setFailReason("Bad Request");
                responseToReturn.setCardManagementResponse(cardActivateResp);
                responseToReturn.setWalletId("");
                responseToReturn.setStatusCode("IB400");
                responseToReturn.setStatusDescription("SYS_ERROR");
                responseToReturn.setFailReason("Bad Request");
            }

            log.info("Getting the card by ref successful");

            Date now = new Date();

            if (activateCardRequest.getActivationCode().equals(cardToActive.getBnkDlAcct().getDe01())) {

                if (cardToActive.getBnkDlAcct().getOtpExpTime().after(now)) {

                    log.info("Activating the card by setting the De02 = \"Y\".");

                    cardToActive.getBnkDlAcct().setDe02("Y");
                    //int result =
                    inappInstrumentDao.save(cardToActive);

                    log.info("Deleting the card by setting the De02 = \"Y\" finished.");

                    try {
                        log.info("Deleting the card by setting the De02 = \"Y\" successful.");

                        cardActivateResp.setCardNo(cardToActive.getAcctNo());
                        cardActivateResp.setCardRef(activateCardRequest.getCardRef());
                        cardActivateResp.setCardType(cardToActive.getAcctTyp().startsWith("OCP") ? cardToActive.getAcctTyp().split("-")[1] : cardToActive.getAcctTyp());
                        cardActivateResp.setIsDefault(cardToActive.getBnkDlAcct().getIsDef().equals("Y"));
                        cardActivateResp.setIsActive(cardToActive.getBnkDlAcct().getDe02().equals("Y"));
                        cardActivateResp.setCardBin(cardToActive.getBnkDlAcct().getCrdBin());

                        cardActivateResp.setStatusCode("IB200");
                        cardActivateResp.setStatusDescription("SUCCESS");
                        cardActivateResp.setFailReason("");

                        cardActivateResp.setMaskedCardNumber(cardToActive.getBnkDlAcct().getDe04());
                        responseToReturn.setCardManagementResponse(cardActivateResp);
                        responseToReturn.setWalletId(activateCardRequest.getWalletId());
                        responseToReturn.setStatusCode("IB200");
                        responseToReturn.setStatusDescription("SUCCESS");
                        responseToReturn.setFailReason("");

                        return responseToReturn;
                    } catch (Exception e) {
                        log.error("Deleting the card by setting the De02 = \"Y\" failed.");

                        cardActivateResp.setCardNo(cardToActive.getAcctNo());
                        cardActivateResp.setCardRef(activateCardRequest.getCardRef());
                        cardActivateResp.setCardType(cardToActive.getAcctTyp().startsWith("OCP") ? cardToActive.getAcctTyp().split("-")[1] : cardToActive.getAcctTyp());
                        cardActivateResp.setIsDefault(cardToActive.getBnkDlAcct().getIsDef().equals("Y"));
                        cardActivateResp.setIsActive(cardToActive.getBnkDlAcct().getDe02().equals("Y"));
                        cardActivateResp.setCardBin(cardToActive.getBnkDlAcct().getCrdBin());
                        cardActivateResp.setStatusCode("IB603");
                        cardActivateResp.setStatusDescription("SYS_ERROR");
                        cardActivateResp.setFailReason("System error.");
                        responseToReturn.setCardManagementResponse(cardActivateResp);
                        responseToReturn.setWalletId("");
                        responseToReturn.setStatusCode("IB603");
                        responseToReturn.setStatusDescription("SYS_ERROR");
                        responseToReturn.setFailReason("System error.");

                        return responseToReturn;
                    }


                } else {
                    cardActivateResp.setCardNo(cardToActive.getAcctNo());
                    cardActivateResp.setCardRef(activateCardRequest.getCardRef());
                    cardActivateResp.setCardType(cardToActive.getAcctTyp().startsWith("OCP") ? cardToActive.getAcctTyp().split("-")[1] : cardToActive.getAcctTyp());
                    cardActivateResp.setIsDefault(cardToActive.getBnkDlAcct().getIsDef().equals("Y"));
                    cardActivateResp.setIsActive(cardToActive.getBnkDlAcct().getDe02().equals("Y"));
                    cardActivateResp.setCardBin(cardToActive.getBnkDlAcct().getCrdBin());

                    cardActivateResp.setStatusCode("IB400");
                    cardActivateResp.setStatusDescription("Bad Request");
                    cardActivateResp.setFailReason("OTP is expired.");
                    cardActivateResp.setExtErrorCode("ERR_26_19");

                    responseToReturn.setCardManagementResponse(cardActivateResp);
                    responseToReturn.setWalletId("");
                    responseToReturn.setStatusCode("IB400");
                    responseToReturn.setStatusDescription("Bad Request");
                    responseToReturn.setFailReason("OTP is expired.");
                    responseToReturn.setExtErrorCode("ERR_26_19");

                    return responseToReturn;
                }
            } else {
                cardActivateResp.setCardNo(cardToActive.getAcctNo());
                cardActivateResp.setCardRef(activateCardRequest.getCardRef());
                cardActivateResp.setCardType(cardToActive.getAcctTyp().startsWith("OCP") ? cardToActive.getAcctTyp().split("-")[1] : cardToActive.getAcctTyp());
                cardActivateResp.setIsDefault(cardToActive.getBnkDlAcct().getIsDef().equals("Y"));
                cardActivateResp.setIsActive(cardToActive.getBnkDlAcct().getDe02().equals("Y"));
                cardActivateResp.setCardBin(cardToActive.getBnkDlAcct().getCrdBin());

                cardActivateResp.setStatusCode("IB400");
                cardActivateResp.setStatusDescription("Bad Request");
                cardActivateResp.setExtErrorCode("ERR_26_18");

                cardActivateResp.setFailReason("Activation code is incorrect.");

                responseToReturn.setCardManagementResponse(cardActivateResp);
                responseToReturn.setWalletId("");
                responseToReturn.setStatusCode("IB400");
                responseToReturn.setStatusDescription("Bad Request");
                responseToReturn.setFailReason("Activation code is incorrect.");
                responseToReturn.setExtErrorCode("ERR_26_18");

                return responseToReturn;
            }
        } catch (Exception e) {
            String errMessage = e.getMessage();

            log.error(errMessage);
            log.error(e.getMessage(), e);

            responseToReturn.setStatusCode("IB603");
            responseToReturn.setStatusDescription("System Error.");
            responseToReturn.setFailReason(errMessage);

            return responseToReturn;
        }

    }

    @Override
    public CheckCardInUseResponse postCheckTheCardInUse(CheckCardInUseRequest checkCardInUseRequest) {

        CheckCardInUseResponse responseToReturn = new CheckCardInUseResponse();

        responseToReturn.setStatusCode("IB200");
        responseToReturn.setStatusDescription("Success.");
        responseToReturn.setFailReason("");
        responseToReturn.setExtErrorCode("");

        if (checkCardInUseRequest.getMerchantId() == null || checkCardInUseRequest.getMerchantId().isEmpty()) {
            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("Merchant id cannot be null.");
            responseToReturn.setExtErrorCode("ERR_26_09");
            log.error("Merchant id cannot be null.");

            return responseToReturn;
        }

        if (checkCardInUseRequest.getBankCode() == null || checkCardInUseRequest.getBankCode().isEmpty()) {
            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("Bank Code cannot be null.");
            responseToReturn.setExtErrorCode("ERR_26_09");
            log.error("Bank Code cannot be null.");

            return responseToReturn;
        }

        if (checkCardInUseRequest.getCardNo() == null || checkCardInUseRequest.getCardNo().isEmpty()) {
            responseToReturn.setStatusCode("IB400");
            responseToReturn.setStatusDescription("Bad Request");
            responseToReturn.setFailReason("Card Number cannot be null.");
            responseToReturn.setExtErrorCode("ERR_26_09");
            log.error("Card Number cannot be null.");

            return responseToReturn;
        }

        try {

            String result = "-";

            List<BnkDmAcct> findByCardNo = inappInstrumentDao.findByCardNo(checkCardInUseRequest.getBankCode(), checkCardInUseRequest.getCardNo());

            for (BnkDmAcct bnkDmAccounts : findByCardNo) {

                BnkDlUsr externalUserDataByUserId = userManagementBo.getExternalUserDataByUserId(bnkDmAccounts.getUsrId(), checkCardInUseRequest.getMerchantId());

                if (externalUserDataByUserId != null) {
                    result = externalUserDataByUserId.getExtId();
                    break;
                }
            }

            responseToReturn.setStatusCode("IB200");
            responseToReturn.setStatusDescription("SUCCESS");
            responseToReturn.setFailReason("");
            responseToReturn.setWalletId(result);
        } catch (Exception e) {
            String errMessage = e.getMessage();

            log.error(errMessage);
            log.error(e.getMessage(), e);

            responseToReturn.setStatusCode("IB603");
            responseToReturn.setStatusDescription("System Error.");
            responseToReturn.setFailReason(errMessage);
        }
        return responseToReturn;
    }

    @Override
    public List<BnkDmAcct> getUserExternalCardsWithSVC(String userid, String comid, String bankCode) {

        log.info("Getting user card list");

        List<BnkDmAcct> existingUserCardList = inappInstrumentDao.findByUser(userid, comid, bankCode);

        log.info("Getting user card list finished");

        return existingUserCardList;
    }

    @Override
    public BalanceList postGetBalanceList(ListExternalCardRequest balanceListRequest) {
        BalanceList balanceListResponse = new BalanceList();
        String bankCode;
        ListExternalCardRequest cardRequest;
        balanceListResponse.setWalletId(balanceListRequest.getWalletId());

        // Get all cards
        cardRequest = new ListExternalCardRequest();
        cardRequest.setMerchantId(balanceListRequest.getMerchantId());
        cardRequest.setWalletId(balanceListRequest.getWalletId());

        ListExternalCardResponse cards = postListAllUserCards(cardRequest);

        if (!cards.getStatusCode().equalsIgnoreCase("IB200")) {
            balanceListResponse.setStatusCode(cards.getStatusCode());
            balanceListResponse.setStatusDescription(cards.getStatusDescription());
            balanceListResponse.setFailReason(cards.getFailReason());
            return balanceListResponse;
        }

        // Get bank code
        try {
            log.debug("getExternalUserData of UserManagementBo called.");
            BnkDlUsr extUserDetails = userManagementBo.getExternalUserData(balanceListRequest.getWalletId(), balanceListRequest.getMerchantId());
            log.debug("getExternalUserData of UserManagementBo finished.");

            if (extUserDetails.getExtId() == null || extUserDetails.getExtId().isEmpty()
                    || extUserDetails.getBnkDlUsrPK() == null || extUserDetails.getBnkDlUsrPK().getBnkCode() == null) {
                balanceListResponse.setStatusCode("IB400");
                balanceListResponse.setStatusDescription("Bad Request");
                balanceListResponse.setFailReason("Requested record cannot be found.");

                log.info("Requested record cannot be found.");
                return balanceListResponse;
            }

            bankCode = extUserDetails.getBnkDlUsrPK().getBnkCode();
        } catch (Exception e) {
            String errMessage = e.getMessage();

            log.error(errMessage);
            log.error(e.getMessage(), e);

            balanceListResponse.setStatusCode("IB603");
            balanceListResponse.setStatusDescription("System Error. Cannot get bank code.");
            balanceListResponse.setFailReason(errMessage);

            return balanceListResponse;
        }

        // Get balances for all cards
        try {
            for (Card card : cards.getCardList()) {
                String encryptedCardNo = card.getCardNumber();

                Instrument instrument = new Instrument();

                instrument.setExpMonth(card.getExpMonth());
                instrument.setExpYear(card.getExpYear());
                instrument.setInstrumentName(card.getCardHolderName());
                instrument.setInstrumentNumber(encryptedCardNo);
                instrument.setMaskInstrumentNumber(card.getMaskedCardNumber());
                instrument.setInstrumentRef(card.getCardRef());
                instrument.setType(card.getCardType());


                XMLAddons xmlAddons = new XMLAddons();
                xmlAddons.put("cardNo", encryptedCardNo);
                xmlAddons.put("bankCode", bankCode);
                BalanceInquiryResponse balanceInquiryResponse = cardServiceCommunicator.getWalletBalanceInquiry(xmlAddons);

                for (AccountObj accounts : balanceInquiryResponse.getAccount()) {
                    Balance balance = new Balance();
                    balance.setAccount(pciExtract.EncryptString(accounts.getAccountNo()));
                    balance.setCurrency(accounts.getAccountCurr());
                    balance.setBalance(accounts.getAccountBalance());
                    instrument.addBalancesItem(balance);
                }

                balanceListResponse.addInstrumentsItem(instrument);
            }
        } catch (Exception e) {
            String errMessage = e.getMessage();

            log.error(errMessage);
            log.error(e.getMessage(), e);

            balanceListResponse.setStatusCode("IB603");
            balanceListResponse.setStatusDescription("System Error. Cannot get balance.");
            balanceListResponse.setFailReason(errMessage);

            return balanceListResponse;
        }

        balanceListResponse.setStatusCode("IB200");
        balanceListResponse.setStatusDescription("SUCCESS");
        balanceListResponse.setFailReason("");
        return balanceListResponse;
    }
}
