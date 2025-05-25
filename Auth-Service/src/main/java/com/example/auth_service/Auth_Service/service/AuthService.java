package com.example.auth_service.Auth_Service.service;

import com.example.auth_service.Auth_Service.dto.SignUp;
import com.example.auth_service.Auth_Service.entity.UserEntity;
import com.example.auth_service.Auth_Service.repository.UserRepository;
import com.example.auth_service.Auth_Service.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final JwtService jwtService;

    public ResponseEntity<?> createSignUp(SignUp signUp){
        String exists = redisService.getValue(signUp.getName());
        System.out.println("HELLO WORLD"+ exists);
//        if(exists!=null){
//            return ResponseEntity.badRequest().body("Name already exists");
//        }
        UserEntity userEntity = modelMapper.map(signUp,UserEntity.class);
        String hashed = PasswordUtils.hashPassword(signUp.getPassword());
        userEntity.setPassword(hashed);
        userRepository.save(userEntity);
//        redisService.setValue(signUp.getName());
        return ResponseEntity.ok(signUp);
    }

    public ResponseEntity<String> signIn(SignUp signUp){
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(signUp.getEmail());
        if (optionalUserEntity.isPresent()){
            UserEntity userEntity = optionalUserEntity.get();
            if (PasswordUtils.checkPassword(signUp.getPassword(), userEntity.getPassword())){
                return ResponseEntity.ok(jwtService.generateToken(signUp.getEmail()));
            }

        }
        return ResponseEntity.badRequest().body("User not found with Given Email and password");
    }

}
