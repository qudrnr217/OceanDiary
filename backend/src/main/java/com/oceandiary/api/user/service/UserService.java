package com.oceandiary.api.user.service;

import com.oceandiary.api.user.dto.UserRequest;
import com.oceandiary.api.user.dto.UserResponse;
import com.oceandiary.api.user.entity.User;
import com.oceandiary.api.user.exception.UserNotFoundException;
import com.oceandiary.api.user.repository.UserRepository;
import com.oceandiary.api.user.security.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    // 테스트용 TODO: 삭제 필요
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public UserResponse.Login login(UserRequest.Login request) {
        User user = userRepository.findById(request.getId()).orElseThrow(UserNotFoundException::new);
        return UserResponse.Login.build(user, tokenProvider.generateAccessToken(user));
    }
}
