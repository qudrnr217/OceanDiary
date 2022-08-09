package com.oceandiary.api;

import com.oceandiary.api.user.domain.Role;
import com.oceandiary.api.user.domain.SocialProvider;
import com.oceandiary.api.user.entity.User;
import com.oceandiary.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findAll().isEmpty()) {
            User user = User.builder().email("oceandiary@oceandiary.com").provider(SocialProvider.NAVER).name("테스터").birth(LocalDate.of(2022, 8,9)).role(Role.USER).oauthId("test").visitedAt(LocalDateTime.now()).build();
            userRepository.save(user);
        }
    }
}
