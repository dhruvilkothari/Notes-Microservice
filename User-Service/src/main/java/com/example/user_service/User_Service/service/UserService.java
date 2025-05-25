package com.example.user_service.User_Service.service;

import com.example.user_service.User_Service.context.UserContext;
import com.example.user_service.User_Service.dto.UpdateUserRequest;
import com.example.user_service.User_Service.entity.UserEntity;
import com.example.user_service.User_Service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return ResponseEntity.ok(userEntity);
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
}
