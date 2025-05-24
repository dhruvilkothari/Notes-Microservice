package com.example.auth_service.Auth_Service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    private boolean keyExists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
    public void setValue(String key) {
        redisTemplate.opsForValue().set(key, "Exists");
    }
    public String getValue(String key) {
        System.out
                .println("HELLO WORLD"+ keyExists(key));
        return redisTemplate.opsForValue().get(key);
    }

}
