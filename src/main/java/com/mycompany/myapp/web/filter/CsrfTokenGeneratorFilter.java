package com.mycompany.myapp.web.filter;

import java.io.IOException;

//import java.net.MalformedURLException;
//import java.net.URL;
//import javax.servlet.SessionCookieConfig;
//import javax.servlet.http.Cookie;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

public final class CsrfTokenGeneratorFilter extends OncePerRequestFilter {
	
	@Autowired
	ServletContext context;
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
 
        // Spring Security will allow the Token to be included in this header name
        response.setHeader("X-CSRF-HEADER", token.getHeaderName());
 
        // Spring Security will allow the token to be included in this parameter name
        response.setHeader("X-CSRF-PARAM", token.getParameterName());
 
        // this is the value of the token to be included as either a header or an HTTP parameter
        response.setHeader("X-CSRF-TOKEN", token.getToken());
 
// Session Cookie Based Approach for CSRF token
        
//        String pCookieName = "XSRF-TOKEN";
//
//        try {
//            Cookie cookie = new Cookie(pCookieName, token.getToken());
//            URL url = new URL(request.getRequestURL().toString());
//            cookie.setDomain(url.getHost());
//            cookie.setComment("CSRF Token via Session Cookie");
//            cookie.setMaxAge(-1);
//            response.addCookie(cookie);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
 
        filterChain.doFilter(request, response);
    }
    
}

