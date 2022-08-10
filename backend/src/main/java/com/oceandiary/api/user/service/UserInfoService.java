package com.oceandiary.api.user.service;

import com.oceandiary.api.user.dto.UserInfoResponse;
import com.oceandiary.api.user.entity.User;
import com.oceandiary.api.user.exception.UserNotFoundException;
import com.oceandiary.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoService {

    private final UserRepository userRepository;

    public UserInfoResponse getUserInfo(Long userId){
        User findUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return UserInfoResponse.build(findUser);
    }

}
