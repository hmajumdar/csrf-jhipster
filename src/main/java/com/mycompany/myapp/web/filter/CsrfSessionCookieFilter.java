package com.mycompany.myapp.web.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A filter to set the CSRF Token onto a SessionCookie for use by AngularJS
 */
public final class CsrfSessionCookieFilter extends OncePerRequestFilter {
	
	@Autowired
	ServletContext context;
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
 
        // Session Cookie Based Approach for CSRF token
        String pCookieName = "XSRF-TOKEN";

        Cookie cookie = new Cookie(pCookieName, token.getToken());
        cookie.setComment("CSRF Token via Session Cookie");
        cookie.setMaxAge(-1);
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
 
        filterChain.doFilter(request, response);
    }
    
}

