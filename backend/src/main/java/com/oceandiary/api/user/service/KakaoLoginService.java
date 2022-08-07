//package com.oceandiary.api.user.service;
//
//import com.oceandiary.api.user.domain.Role;
//import com.oceandiary.api.user.domain.SocialProvider;
//import com.oceandiary.api.user.dto.KakaoApiResponse;
//import com.oceandiary.api.user.dto.NaverApiResponse;
//import com.oceandiary.api.user.entity.User;
//import com.oceandiary.api.user.repository.SocialLoginUserRepository;
//import com.oceandiary.api.user.request.NaverRequest;
//import com.oceandiary.api.user.response.NaverResponse;
//import com.oceandiary.api.user.security.token.TokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//@Slf4j
//public class KakaoLoginService {
//
//    private final SocialLoginUserRepository socialLoginUserRepository;
//    private final TokenProvider tokenProvider;
//
//    @Value("${KAKAO_API_CLIENT_ID}")
//    private String clientId;
//
//    public NaverResponse.LoginResponse login(NaverRequest.LoginRequest request) {
//        String accessToken = getAccessToken(request);
//        log.info("accessToken : " + accessToken );
//        //System.out.printf("accessToken : " + accessToken);
//        String kakaoUniqueId = getKakaoUniqueId(accessToken);
//        System.out.println("kakaoUniqueId" + kakaoUniqueId);
//        User foundUser = socialLoginUserRepository.findByProviderAndOauthId(SocialProvider.KAKAO, kakaoUniqueId);
//        System.out.println("foundUser" + foundUser.toString());
//        NaverResponse.LoginResponse response;
//
//        if (foundUser != null) {
//            response = NaverResponse.LoginResponse.builder()
//                    .name(foundUser.getName())
//                    .userId(foundUser.getId())
//                    .isExist(true)
//                    .accessToken(tokenProvider.generateAccessToken(foundUser).getToken())
//                    .build();
//        } else {
//            response = NaverResponse.LoginResponse.builder()
//                    .isExist(false)
//                    .oauthId(kakaoUniqueId)
//                    .build();
//        }
//        return response;
//    }
//
//    @Transactional
//    public NaverResponse.JoinResponse join(NaverRequest.JoinRequest request) {
//
//        User newUser = User.builder()
//                .email(request.getEmail())
//                .name(request.getName())
//                .birth(request.getBirth())
//                .provider(SocialProvider.KAKAO)
//                .role(Role.USER)
//                .oauthId(request.getOauthId())
//                .build();
//
//        socialLoginUserRepository.save(newUser);
//        // Issue refresh token
//        String refreshToken = tokenProvider.generateRefreshToken(newUser).getToken();
//        newUser.updateRefreshToken(refreshToken);
//
//        NaverResponse.JoinResponse response = NaverResponse.JoinResponse.builder()
//                .accessToken(tokenProvider.generateAccessToken(newUser).getToken())
//                .name(newUser.getName())
//                .userId(newUser.getId())
//                .build();
//
//        return response;
//    }
//
//
//    public String getAccessToken(NaverRequest.LoginRequest request) {
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://kauth.kakao.com")
//                .path("/oauth/token")
//                .queryParam("grant_type", "authorization_code")
//                .queryParam("client_id", clientId)
//                .queryParam("redirect_uri", "http://localhost:8080")
//                .queryParam("code", request.getCode())
//                .encode()
//                .build()
//                .toUri();
//
//        //System.out.print("uri: " + uri);
//        log.info("uri : " + uri );
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<NaverApiResponse.AccessTokenApiResponse> responseEntity
//                = restTemplate.getForEntity(uri, NaverApiResponse.AccessTokenApiResponse.class);
//        log.info(responseEntity.getBody().getAccessToken());
//        return responseEntity.getBody().getAccessToken();
//    }
//
//    public String getKakaoUniqueId(String accessToken) {
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://kapi.kakao.com")
//                .path("/v2/user/me")
//                .encode()
//                .build()
//                .toUri();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + accessToken);
//        HttpEntity request = new HttpEntity(headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<KakaoApiResponse.ProfileApiResponse> responseEntity = restTemplate.exchange(
//                uri,
//                HttpMethod.GET,
//                request,
//                KakaoApiResponse.ProfileApiResponse.class
//        );
//        log.info("KAKAO Unique ID = {}", responseEntity.getBody().getId());
//        return responseEntity.getBody().getId().toString();
//    }
//
//}
