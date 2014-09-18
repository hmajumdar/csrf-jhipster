/*
 * Initial concepts derived from
 * https://github.com/aditzel/spring-security-csrf-token-interceptor/blob/master/src/spring-security-csrf-token-interceptor.js
 *
 * spring-security-csrf-token-interceptor
 *
 * Sets up an interceptor for all HTTP requests that adds the CSRF Token Header that Spring Security requires.
 */
(function () {
    'use strict';
    angular.module('spring-security-csrf-token-interceptor', [])
    .config(function($httpProvider) {
        var defaultCsrfTokenHeader = 'X-CSRF-TOKEN';
        var csrfTokenHeaderName = 'X-CSRF-HEADER';

        var getTokenData = function () {
            var xhr = new XMLHttpRequest();
            xhr.open('head', '/', false);
            xhr.send();
            var csrfTokenHeader = xhr.getResponseHeader(csrfTokenHeaderName);
            csrfTokenHeader = csrfTokenHeader ? csrfTokenHeader : defaultCsrfTokenHeader;
            return { headerName: csrfTokenHeader, token: xhr.getResponseHeader(csrfTokenHeader) };
        };
        var getTokenDataFromResponse = function (response) {
            var csrfTokenHeader = response.headers(csrfTokenHeaderName);
            csrfTokenHeader = csrfTokenHeader ? csrfTokenHeader : defaultCsrfTokenHeader;
            return { headerName: csrfTokenHeader, token: response.headers(csrfTokenHeader) };
        };

        var csrfTokenData = getTokenData();
        $httpProvider.interceptors.push(function($q) {
            return {
                request: function(config) {
                    config.headers[csrfTokenData.headerName] = csrfTokenData.token;
                    return config || $q.when(config);
                },
                response: function(response) {
                    //pull next token data from response
                    csrfTokenData = getTokenDataFromResponse(response);

                    return response;
                }
            };
        });
    });
})();