README for csrfhipster
==========================

CSRF protection in Spring Security 3.2.x is enabled by default with Java configuration. https://spring.io/blog/2013/08/21/spring-security-3-2-0-rc1-highlights-csrf-protection/
JSP Template Engine such as Thymeleaf has built-in support when using Spring CSRF, however must do some extra configuration changes when it comes to client side MVC frameworks such as AngularJS.

Angular provides its own mechanism to counter XSRF. It provides us with $http service that reads the CSRF token from a cookie (by default, XSRF-TOKEN) and sets it as an HTTP header (X-XSRF-TOKEN). 

So in my first attempt I created a session-cookie in the server which I sent back to the client using a custom filter. My expectation was that Angular will pick up the cookie token automatically, but that din't happen :(. Others have however made it work when using frameworks such as Django.  It could due to the way my code sends back the cookie, find it in the commented code [here](https://github.com/HimalayFei/csrf-jhipster/blob/master/src/main/java/com/mycompany/myapp/web/filter/CsrfTokenGeneratorFilter.java)


##Solution/Workaround

We know Spring Security 3.2.x is enabled by default with Java configuration, however we must find a way to send the generated CSRF token to Angular. One way to do that is to send the token back in response headers and write a custom [angular interceptor](https://github.com/HimalayFei/csrf-jhipster/blob/master/src/main/webapp/scripts/spring-security-csrf-token-interceptor.js) to ping the server to get that token before submitting any request. 


Here is a simpified [sequence diagram](https://raw.githubusercontent.com/HimalayFei/csrf-jhipster/dfaa04da5cf4bbd3d6f07a46d3b3ea5376d5d3c7/CSRFSequence.jpg)

The obvious **drawback** I find in this approach is having to ping the server before every put/post call, but it works.



##References:

**Spring CSRF**: https://spring.io/blog/2013/08/21/spring-security-3-2-0-rc1-highlights-csrf-protection/

**Angular-CSRF**: https://docs.angularjs.org/api/ng/service/$http

**Angular-Django**: http://angularjs-best-practices.blogspot.com/2013/07/angularjs-and-xsrfcsrf-cross-site.html

**Angular-Spring CSRF Interceptor**: https://github.com/aditzel/spring-security-csrf-token-interceptor 

**My Enable-CSRF Changeset**:  https://github.com/HimalayFei/csrf-jhipster/commit/c30fcacbaab6eae33ade60385e2e5a28a1234427




