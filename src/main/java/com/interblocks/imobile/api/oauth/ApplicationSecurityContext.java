/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.oauth;

import com.interblocks.iwallet.oauth.UserPrincipal;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * @author ashan-mac
 */
public class ApplicationSecurityContext implements SecurityContext {

    private UserPrincipal user;
    private String scheme;

    public ApplicationSecurityContext(UserPrincipal user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.user;
    }

    @Override
    public boolean isUserInRole(String s) {
        if (user.getRole() != null) {
            return user.getRole().contains(s);
        }
        return false;
    }

    @Override
    public boolean isSecure() {
        return "https".equals(this.scheme);
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }

}
