package com.oceandiary.api.user.service;

import com.oceandiary.api.user.domain.Role;
import com.oceandiary.api.user.domain.SocialProvider;
import com.oceandiary.api.user.entity.User;
import com.oceandiary.api.user.repository.SocialLoginUserRepository;
import com.oceandiary.api.user.request.JoinRequest;
import com.oceandiary.api.user.request.LoginRequest;
import com.oceandiary.api.user.response.JoinResponse;
import com.oceandiary.api.user.response.LoginResponse;
import com.oceandiary.api.user.security.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final SocialLoginUserRepository socialLoginUserRepository;
    private final TokenProvider tokenProvider;

    // TODO: @ConfigurationProperties 혹은 @Value 사용해서 API 키 감추기
    @Value("${NAVER_API_CLIENT_ID}")
    private String clientId;
    @Value("${NAVER_API_CLIENT_SECRET}")
    private String clientSecret;

    public LoginResponse naverLogin(LoginRequest request, HttpSession session){
        String accessToken = getAccessToken(request, session);
        String naverUniqueId = getNaverUniqueId(accessToken);

        User foundUser = socialLoginUserRepository.findByProviderAndOauthId(SocialProvider.NAVER, naverUniqueId);
        return checkUser(foundUser, naverUniqueId);
    }

    public JoinResponse join(JoinRequest request){
        User newUser = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .birth(request.getBirth())
                .provider(SocialProvider.NAVER)
                .role(Role.USER)
                .oauthId(request.getOauthId())
                .build();

        socialLoginUserRepository.save(newUser);
        // Issue refresh token
        String refreshToken = tokenProvider.generateRefreshToken(newUser).getToken();
        newUser.updateRefreshToken(refreshToken);

        JoinResponse response = JoinResponse.builder()
                .accessToken(tokenProvider.generateAccessToken(newUser).getToken())
                .name(newUser.getName())
                .userId(newUser.getId())
                .build();

        return response;
    }



    public LoginResponse checkUser(User foundUser, String uniqueId){
        LoginResponse response;
        if(foundUser != null){
            response = LoginResponse.builder()
                    .name(foundUser.getName())
                    .userId(foundUser.getId())
                    .isExist(true)
                    .accessToken(tokenProvider.generateAccessToken(foundUser).getToken())
                    .build();
        }else{
            response = LoginResponse.builder()
                    .isExist(false)
                    .oauthId(uniqueId)
                    .build();
        }
        return response;
    }

    public String getAccessToken(LoginRequest request, HttpSession session){
        return null;
    }

    public String getNaverUniqueId(String accessToken){
        return null;
    }
}
