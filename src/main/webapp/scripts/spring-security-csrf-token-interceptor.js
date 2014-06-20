(function () {
    'use strict';
    angular.module('spring-security-csrf-token-interceptor', [])
    .config(function($httpProvider) {
        var getTokenData = function() {
            var defaultCsrfTokenHeader = 'X-CSRF-TOKEN';
            var csrfTokenHeaderName = 'X-CSRF-HEADER';
            var xhr = new XMLHttpRequest();
            xhr.open('head', '/', false);
            xhr.send();
            var csrfTokenHeader = xhr.getResponseHeader(csrfTokenHeaderName);
            csrfTokenHeader = csrfTokenHeader ? csrfTokenHeader : defaultCsrfTokenHeader;
            return { headerName: csrfTokenHeader, token: xhr.getResponseHeader(csrfTokenHeader) };
        };
        var csrfTokenData = getTokenData();
        $httpProvider.interceptors.push(function($q) {
            return {
                request: function(config) {
                    if (config.method.toLowerCase() === "post" || config.method.toLowerCase() === "put") {
                        csrfTokenData = getTokenData();
                    }
                    config.headers[csrfTokenData.headerName] = csrfTokenData.token;
                    return config || $q.when(config);
                }
            };
        });
    });
})();