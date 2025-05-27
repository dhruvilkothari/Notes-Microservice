package com.example.connection_service.Connection_Service.client;

import com.example.connection_service.Connection_Service.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "User-Service")
public interface ConnectionUser {
    @GetMapping("/api/v1/user/findUser")
    public UserEntity findUser(@RequestParam("email") String email);

    @GetMapping("/api/v1/user/findById")
    public UserEntity findById(@RequestParam("id") Long id);

    @PutMapping("/api/v1/user/{userId}/following/{followingId}")
    public ResponseEntity<?> followUser(@RequestParam("userId") Long userId, @RequestParam("followingId") Long followingId);

}
