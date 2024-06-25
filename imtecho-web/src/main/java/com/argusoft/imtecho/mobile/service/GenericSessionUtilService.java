/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.imtecho.mobile.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author kunjan
 */
public class GenericSessionUtilService {
    @Context
    protected HttpServletRequest request;
    @Context
    protected HttpServletResponse response;
    
    private static final String USER_TOKEN = "USER_TOKEN";
    
    public HttpSession getSession() {
        return request.getSession();
    }
    
    public void addCookie(HttpServletResponse response, String name, String value) {

        Cookie cookie = new Cookie(name, value);
        response.addCookie(cookie);
    }
    
    public String getCookie(HttpServletRequest request, String name) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (name.endsWith(cookie.getName())) {
                return cookie.getValue();
            }
        }
        throw new IllegalArgumentException("Cookie " + name + " not found.");
    }

    public void removCookie(HttpServletResponse response, String name) {

        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }
    
     public void addToken(HttpServletResponse response, String token) {

        addCookie(response, USER_TOKEN, token);
    }
    
    public String getToken(String token) {

        if (StringUtils.isBlank(token)) {
            return getCookie(request, USER_TOKEN);
        } else {
            addToken(response, token);
        }
        return token;
    }
    
    public void removToken(HttpServletResponse response) {

        removCookie(response, USER_TOKEN);

    }
}