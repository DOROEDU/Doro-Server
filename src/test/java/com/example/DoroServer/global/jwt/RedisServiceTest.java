package com.example.DoroServer.global.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    @DisplayName("setValues, getValues 테스트")
    public void testRedisSetAndGet() {
        // given
        String key = "test";
        String value = "test";

        // when
        redisService.setValues(key, value);
        String returnedValue = redisService.getValues(key);

        // then
        assertThat(returnedValue).isEqualTo(value);

        // after
        redisService.deleteValues(key);

    }
}