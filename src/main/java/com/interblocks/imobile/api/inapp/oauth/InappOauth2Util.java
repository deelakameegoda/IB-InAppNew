package com.interblocks.imobile.api.inapp.oauth;

import com.interblocks.iwallet.oauth.PCIExtract;
import com.interblocks.iwallet.oauth.UserPrincipal;
import com.interblocks.iwallet.util.Const;
import com.interblocks.iwallet.util.HashingUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.impl.DefaultJwtParser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Anusha Ariyathilaka @ Interblocks Ltd.
 */
@Log4j2
@Component
public class InappOauth2Util {

    @Autowired
    InappTokenCipher tokenCipher;

    @Autowired
    PCIExtract pciextract;

    @Value("${default.bank_code}")
    private Integer defaultBankCode;
    @Value("${inapp.token_validity_timeout}")
    private Integer inappTokenValidityTimeout;

    public String decodeToken(String auth) {
        auth = auth.replaceFirst("[B|b]earer ", "");
        return auth;
    }

    public boolean isValidToken(String accessToken) {
        try {
            accessToken = tokenCipher.decipherToken(accessToken, Const.AUTH_KEY.getBytes());
        } catch (Exception ex) {

            Logger.getLogger(InappOauth2Util.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }


        JwtParser parser = new DefaultJwtParser();

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(Const.AUTH_KEY.getBytes())
                .parseClaimsJws(accessToken);

        String username = claims.getHeader().get("UserName").toString();
        // String subject = claims.getBody().get(Claims.SUBJECT, String.class);
        long expTime = claims.getBody().get(Claims.EXPIRATION, Long.class);

        // JWT jwt = new JWTReader().read(accessToken);
        //String username = jwt.getHeader().getCustomField("UserName", String.class);

        // long expTime = jwt.getClaimsSet().getExpirationTime();

        if (new Date().before(new Date(expTime))) {
            Logger.getLogger(InappOauth2Util.class.getName()).log(Level.SEVERE, "Token expired");
            return false;
        } else {
            return username != null;
        }

    }

    UserPrincipal getUserPrincipal(String accessToken) {

        try {
            accessToken = tokenCipher.decipherToken(accessToken, Const.AUTH_KEY.getBytes());
        } catch (Exception ex) {
            Logger.getLogger(InappOauth2Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        JwtParser parser = new DefaultJwtParser();

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(Const.AUTH_KEY.getBytes())
                .parseClaimsJws(accessToken);

        String username = claims.getHeader().get("UserName").toString();
        //String subject = claims.getBody().get(Claims.SUBJECT, String.class);
        String bankCode = claims.getHeader().get("BankCode").toString();

        //JWT jwt = new JWTReader().read(accessToken);
        //String username = jwt.getHeader().getCustomField("UserName", String.class);
        // String bankCode = jwt.getHeader().getCustomField("BankCode", String.class);
        // String encSignature = jwt.getSignature();

        log.info("UserName:" + username);
        log.info("BankCode:" + bankCode);
        // System.out.println("encSignature:" + encSignature);

        String clientId = claims.getHeader().get("client_id").toString();


        System.out.println("clientId:" + clientId);

        UserPrincipal principal = new UserPrincipal();
        principal.setId(username);
        principal.setBankCode(bankCode);
        principal.setClientId(clientId);
        principal.setToken(accessToken);

        return principal;

    }

    public String generateToken(String userName, String bankCode) throws Exception {

        Date currentTime = new Date();

        long issuedAt = currentTime.getTime() / 1000;
        long expiredAt = issuedAt + inappTokenValidityTimeout;
        String clientId = "IPAY";

        String tokenHashString = userName.toUpperCase() + defaultBankCode + "api.imobile.com" + "interbloks.com" + "106422453082479998429" + Long.toString(issuedAt) + Long.toString(expiredAt);
        String tokenHash = new HashingUtil().sha256Hex(tokenHashString.getBytes());
        String atHash = new HashingUtil().sha256Hex(tokenHash.getBytes());

        // generate jwtToken
        String encodedJWT = null;


        JwtBuilder jwtBuilder = new DefaultJwtBuilder();

        jwtBuilder.setHeaderParam("UserName", userName.toUpperCase());
        jwtBuilder.setHeaderParam("BankCode", defaultBankCode);
        jwtBuilder.setHeaderParam("client_id", (clientId));
        jwtBuilder.setHeaderParam("token_hash", tokenHash);
        jwtBuilder.setHeaderParam("at_hash", atHash);


        HashMap claimsMap = new HashMap();
        claimsMap.put(Claims.AUDIENCE, "api.imobile.com");
        claimsMap.put(Claims.ISSUER, "interbloks.com");
        claimsMap.put(Claims.SUBJECT, "login");
        claimsMap.put(Claims.EXPIRATION, expiredAt / 1000);
        claimsMap.put(Claims.ISSUED_AT, issuedAt / 1000);


        jwtBuilder.setClaims(claimsMap);

        jwtBuilder.signWith(SignatureAlgorithm.HS256, Const.AUTH_KEY.getBytes());
        encodedJWT = jwtBuilder.compact();
        encodedJWT = tokenCipher.cipherToken(encodedJWT, Const.AUTH_KEY.getBytes());


/**
 JWT jwt = null;
 try {
 jwt = new JWT.Builder().setHeaderAlgorithm(// header
 "RS256")
 .setHeaderCustomField("UserName", userName.toUpperCase())
 .setHeaderCustomField("BankCode", bankCode)
 .setClaimsSetAudience(// claimset
 "api.imobile.com").setClaimsSetIssuer("interbloks.com")
 .setClaimsSetSubject("login")
 .setClaimsSetExpirationTime(expiredAt)
 .setClaimsSetIssuedAt(issuedAt)
 //.setClaimsSetCustomField("id", "106422453082479998429")
 // .setClaimsSetCustomField("verified_email", "true")
 //.setClaimsSetCustomField("email_verified", "true")
 // .setClaimsSetCustomField("cid", "788732372078.apps.googleusercontent.com")
 // .setClaimsSetCustomField("azp", "788732372078.apps.googleusercontent.com")
 // .setClaimsSetCustomField("email", "antonio.sanso@gmail.com")
 .setClaimsSetCustomField("client_id", clientId)
 .setClaimsSetCustomField("token_hash", tokenHash)
 .setClaimsSetCustomField("at_hash", atHash)
 .setSignature(new MD5Generator().generateValue(clientId + "::::" + issuedAt)).build(); // signature

 encodedJWT = new JWTWriter().write(jwt);
 encodedJWT = tokenCipher.cipherToken(encodedJWT, Const.AUTH_KEY.getBytes());
 **/
        /**
         } catch (OAuthSystemException ex) {
         Logger.getLogger(ResourceOwnerPasswordCredentialsGrant.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
         Logger.getLogger(ResourceOwnerPasswordCredentialsGrant.class.getName()).log(Level.SEVERE, null, ex);
         }
         **/
        //return pciExtract.EncryptString(encodedJWT);
        return encodedJWT;
    }

}
