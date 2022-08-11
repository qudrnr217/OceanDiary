package com.oceandiary.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Slf4j
@Configuration
public class RedisConfig {
    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() throws IOException {
        redisServer = new RedisServer(redisPort);

        /**
         * 스프링이 비정상적으로 종료(ctrl + c)되었을 때 embedded redis server가 종료되지 않는 버그가 있습니다.
         * 따라서 이미 redis server가 실행중이라면 다시 실행하지 않게 하기 위해 다음과 같이 작성합니다.
         * */
        try {
            redisServer.start();
        } catch (Exception e) {
            log.info("Redis 서버가 이미 실행 중: 기존 레디스 서버를 사용합니다.");
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

}