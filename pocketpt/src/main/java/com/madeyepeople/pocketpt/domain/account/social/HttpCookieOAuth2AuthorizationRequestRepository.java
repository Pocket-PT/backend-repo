package com.madeyepeople.pocketpt.domain.account.social;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirectUri";
    private static final int cookieExpireSeconds = 180;

    /** cookie 에 저장되어있던 authorizationRequest 들을 가져온다 authorizationUri, authorizationGrantType, responseType, clientId, redirectUri, scopes, additionalParameters */
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtil.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    /** 플랫폼으로 보내기 위한 Request 를 `oauth2_auth_request` 라는  cookie 에 저장 한다 authorizationUri, authorizationGrantType, responseType, clientId, redirectUri, scopes, additionalParameters */
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        log.error("saveAuthorizationRequest : " + authorizationRequest.getRedirectUri());
        log.error("saveAuthorizationRequest : " + authorizationRequest.getAuthorizationUri());
        log.error("saveAuthorizationRequest : " + authorizationRequest.getAuthorizationRequestUri());
        if (authorizationRequest == null) {
            removeAuthorizationRequest(request, response);
            return;
        }

        CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookieUtil.serialize(authorizationRequest), cookieExpireSeconds);

        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtil.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds);
        }
    }

    /** remove 를 재정의 해서 cookie 를 가져오고 remove 는 successHandler 또는 failureHandler 에서 할 수 있도록 한다 */
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    /**
     * 사용자 정보를 다 가지고 온 뒤 이제 리다이렉트를 하면 기존에 남아있던 쿠키들을 제거해주기 위해 사용된다
     *  OAuth2AuthorizationRequest 와 클라이언트에서 파리미터로 요청한 redirect_uri 가 된다
     * */
    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    }
}