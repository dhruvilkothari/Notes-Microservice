package com.example.user_service.User_Service.service;

import com.example.user_service.User_Service.context.UserContext;
import com.example.user_service.User_Service.dto.UpdateUserRequest;
import com.example.user_service.User_Service.entity.UserEntity;
import com.example.user_service.User_Service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<UserEntity> getCurrentUser(){
        String email = UserContext.getEmail();
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return ResponseEntity.ok(userEntity);
    }

    public ResponseEntity<?> updateUser(UpdateUserRequest updateRequest){
        String email = UserContext.getEmail();
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Update properties
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isEmpty()) {
            userEntity.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getName() != null && !updateRequest.getName().isEmpty()) {
            userEntity.setName(updateRequest.getName());
        }

        // Save updated user
        UserEntity updatedUser = userRepository.save(userEntity);

        log.info("User updated: {}", updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    public ResponseEntity<?> deleteUser(){
        String email = UserContext.getEmail();
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        userRepository.delete(userEntity);

        log.info("User deleted: {}", userEntity.getId());
        return ResponseEntity.ok("User account deleted successfully");
    }


    public ResponseEntity<?> findUser(String email) {
        log.info("User Entity in "+ email);
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found with email: " + email);
        }
        return ResponseEntity.ok(userEntity.get());
    }

    public ResponseEntity<?> findUserById(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found with id: " + id);
        }

        return ResponseEntity.ok(optionalUser.get());
    }
    @Transactional
    public ResponseEntity<?> followUser(Long userId, Long followingId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        Optional<UserEntity> followingOptional = userRepository.findById(followingId);

        if (userOptional.isEmpty() || followingOptional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User or following user not found");
        }

        UserEntity user = userOptional.get();
        UserEntity following = followingOptional.get();

            if (user.getFollowing().contains(following)) {
            return ResponseEntity.ok(Collections.singletonMap("message", "User is already following the specified user"));

        }

        following.getFollowers().add(user);
        userRepository.save(following);
        user.getFollowing().add(following);
        userRepository.save(user);


        return ResponseEntity.ok().build();
    }
}
