/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interblocks.imobile.api.inapp.oauth;

import com.interblocks.imobile.api.oauth.ApplicationSecurityContext;
import com.interblocks.iwallet.oauth.UserPrincipal;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.FilterConfig;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

/**
 * @author Anusha Ariyathilaka @ Interblocks Ltd.
 */
@Log4j2
@Component
@Provider
@PreMatching
public class InappAuthFilter implements ContainerRequestFilter {

    private static final boolean DEBUG = true;
    @Autowired
    InappOauth2Util oauth2Util;
    private FilterConfig filterConfig = null;

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (DEBUG) {
                log("ApiFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("ApiFilter()");
        }
        StringBuffer sb = new StringBuffer("ApiFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    public void log(String msg) {
        log.info(msg);
        System.out.println(msg);
    }

    @Override
    public void filter(ContainerRequestContext containerRequest) {
        log.debug(" ----- Incoming Request ----");

        log.debug("METHOD  :  " + containerRequest.getMethod());
        log.debug("PATH    :  " + containerRequest.getUriInfo().getPath(true));

        log.debug("----***** HEADERS *******----");
        MultivaluedMap<String, String> map = containerRequest.getHeaders();

        Set keys = map.keySet();
        for (Object key : keys) {
            log.debug("     HEADER : " + key + "  :::  " + map.get(key));
        }
        log.debug(" ----- Incoming Request END ----");

        //GET, POST, PUT, DELETE, ...
        String method = containerRequest.getMethod();

        log.debug("METHOD = " + method);
        // myresource/get/56bCA for example
        String path = containerRequest.getUriInfo().getPath(true);

        //We do allow wadl to be retrieve
        if (method.equals("OPTIONS")) {
            return;
        }

        if (method.equals("POST") && (path.equals("user/login"))) {

            return;
        }

        if (method.equals("POST") && (path.equals("registration/register"))) {
            return;
        }
        //Get the authentification passed in HTTP headers parameters
        String auth = containerRequest.getHeaderString("authorization");

        //If the user does not have the right (does not provide any HTTP Basic Auth)
        if (auth == null) {
            log("Unauthorized Request : " + " Token Not found " + method + " :: " + path);
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        //lap : loginAndPassword
        String accessToken = oauth2Util.decodeToken(auth);

        //If login or password fail
        if (accessToken == null || accessToken.isEmpty()) {
            log("Unauthorized Request : " + " Token Not found or Empty " + method + " :: " + path);
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        //DO YOUR DATABASE CHECK HERE (replace that line behind)...
        // Validate the access token
        if (!oauth2Util.isValidToken(accessToken)) {
            log("Unauthorized Request : " + " Invalid Token " + method + " :: " + path);
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);

        } else {

            // We configure your Security Context here
            UserPrincipal userId = oauth2Util.getUserPrincipal(accessToken);
            log("Authorized Request : " + " Valid Token for client " + userId.getClientId() + "  " + method + " :: " + path);
            String scheme = containerRequest.getUriInfo().getRequestUri().getScheme();
            containerRequest.setSecurityContext(new ApplicationSecurityContext(userId, scheme));

            // return true;
        }
    }

}
