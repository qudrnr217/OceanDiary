package com.oceandiary.api.user.service;

import com.oceandiary.api.user.domain.Role;
import com.oceandiary.api.user.domain.SocialProvider;
import com.oceandiary.api.user.dto.NaverApiResponse;
import com.oceandiary.api.user.entity.User;
import com.oceandiary.api.user.repository.NaverUserRepository;
import com.oceandiary.api.user.request.NaverRequest;
import com.oceandiary.api.user.response.NaverResponse;
import com.oceandiary.api.user.security.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${NAVER_API_CLIENT_ID}")
    private String clientId;
    @Value("${NAVER_API_CLIENT_SECRET}")
    private String clientSecret;

    public NaverResponse.LoginResponse login(NaverRequest.LoginRequest request, HttpSession session) {
        String accessToken = getAccessToken(request, session);
        String naverUniqueId = getNaverUniqueId(accessToken);

        User foundUser = naverUserRepository.findByProviderAndOauthId(SocialProvider.NAVER, naverUniqueId);
        NaverResponse.LoginResponse response;

        if (foundUser != null) {
            response = NaverResponse.LoginResponse.builder()
                    .name(foundUser.getName())
                    .userId(foundUser.getId())
                    .isExist(true)
                    .accessToken(tokenProvider.generateAccessToken(foundUser).getToken())
                    .build();
        } else {
            response = NaverResponse.LoginResponse.builder()
                    .isExist(false)
                    .oauthId(naverUniqueId)
                    .build();
        }
        return response;
    }

    @Transactional
    public NaverResponse.JoinResponse join(NaverRequest.JoinRequest request) {

        User newUser = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .birth(request.getBirth())
                .provider(SocialProvider.NAVER)
                .role(Role.USER)
                .oauthId(request.getOauthId())
                .build();

        naverUserRepository.save(newUser);
        // Issue refresh token
        String refreshToken = tokenProvider.generateRefreshToken(newUser).getToken();
        newUser.updateRefreshToken(refreshToken);

        NaverResponse.JoinResponse response = NaverResponse.JoinResponse.builder()
                .accessToken(tokenProvider.generateAccessToken(newUser).getToken())
                .name(newUser.getName())
                .userId(newUser.getId())
                .build();

        return response;
    }


    public String getAccessToken(NaverRequest.LoginRequest request, HttpSession session) {
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
        ResponseEntity<NaverApiResponse.AccessTokenApiResponse> responseEntity
                = restTemplate.getForEntity(uri, NaverApiResponse.AccessTokenApiResponse.class);
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
        ResponseEntity<NaverApiResponse.ProfileApiResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                NaverApiResponse.ProfileApiResponse.class
        );
        log.info("NAVER Unique ID = {}", responseEntity.getBody().getResponse().getId());
        return responseEntity.getBody().getResponse().getId();
    }


}
