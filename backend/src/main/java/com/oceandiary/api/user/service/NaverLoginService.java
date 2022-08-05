package com.oceandiary.api.user.service;

import com.oceandiary.api.user.repository.NaverUserRepository;
import com.oceandiary.api.user.domain.Role;
import com.oceandiary.api.user.domain.SocialProvider;
import com.oceandiary.api.user.dto.NaverLoginAccessTokenApiResponse;
import com.oceandiary.api.user.dto.NaverLoginProfileApiResponse;
import com.oceandiary.api.user.entity.User;
import com.oceandiary.api.user.request.NaverLoginJoinRequest;
import com.oceandiary.api.user.request.NaverLoginRequest;
import com.oceandiary.api.user.response.NaverLoginJoinResponse;
import com.oceandiary.api.user.response.NaverLoginResponse;
import com.oceandiary.api.user.security.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.net.URI;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class NaverLoginService {

    private final NaverUserRepository naverUserRepository;
    private final TokenProvider tokenProvider;

    // TODO: @ConfigurationProperties 혹은 @Value 사용해서 API 키 감추기
    private final String clientId = "vQpyQoy56bjYmRbrlq5L";
    private final String clientSecret = "TJhH0VSuX_";

    public NaverLoginResponse login(NaverLoginRequest request, HttpSession session) {
        String accessToken = getAccessToken(request, session);
        String naverUniqueId = getNaverUniqueId(accessToken);

        User foundUser = naverUserRepository.findByProviderAndOauthId(SocialProvider.NAVER, naverUniqueId);
        NaverLoginResponse response = new NaverLoginResponse();
        if (foundUser != null) {
            response.setName(foundUser.getName());
            response.setUserId(foundUser.getId());
            response.setExist(true);
            response.setAccessToken(tokenProvider.generateAccessToken(foundUser).getToken());
        } else {
            response.setExist(false);
            response.setOauthId(naverUniqueId);
        }
        return response;
    }

    @Transactional
    public NaverLoginJoinResponse join(NaverLoginJoinRequest request) {

        User newUser = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .birth(request.getBirth())
                .provider(SocialProvider.NAVER)
                .role(Role.USER)
                .oauthId(request.getOauthId()).build();
        // JPA Auditing: created by
        newUser.setCreatedBy(newUser);
        naverUserRepository.save(newUser);

        // Issue refresh token
        String refreshToken = tokenProvider.generateRefreshToken(newUser).getToken();
        newUser.updateRefreshToken(refreshToken);

        // JPA Auditing: updated by
        newUser.setUpdatedBy(newUser);

        NaverLoginJoinResponse response = new NaverLoginJoinResponse();
        response.setAccessToken(tokenProvider.generateAccessToken(newUser).getToken());
        response.setName(newUser.getName());
        response.setUserId(newUser.getId());
        return response;
    }


    public String getAccessToken(NaverLoginRequest request, HttpSession session) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://nid.naver.com")
                .path("/oauth2.0/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", request.getCode())
                .queryParam("state", session.getAttribute("state"))
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<NaverLoginAccessTokenApiResponse> responseEntity = restTemplate.getForEntity(uri, NaverLoginAccessTokenApiResponse.class);
        log.info(responseEntity.getBody().getAccessToken());
        return responseEntity.getBody().getAccessToken();
    }

    public String getNaverUniqueId(String accessToken) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/nid/me")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity request = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<NaverLoginProfileApiResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                NaverLoginProfileApiResponse.class
        );
        log.info("NAVER Unique ID = {}", responseEntity.getBody().getResponse().getId());
        return responseEntity.getBody().getResponse().getId();
    }


}
