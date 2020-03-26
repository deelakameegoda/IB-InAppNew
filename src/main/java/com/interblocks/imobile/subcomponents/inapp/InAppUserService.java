package com.interblocks.imobile.subcomponents.inapp;

import com.interblocks.imobile.api.inapp.model.*;
import com.interblocks.iwallet.model.BnkDlUsr;

public interface InAppUserService {

    UserLoginResponse postValidateUserResponse(ExternalUserLogin User);

    BnkDlUsr getExternalUserData(String walletId, String merchantId);

    BnkDlUsr getExternalUserDataByUserId(String userId, String merchantId);

    CustomerProfile postEditExternalWalletUser(CustomerProfileEditRequest customerProfileEditRequest);

    CustomerProfile postListExternalWalletUser(ListWalletRequest listWalletRequest);

    ExternalWalletUserModel ListExternalUserRequest(String userId, String bankCode);

}
