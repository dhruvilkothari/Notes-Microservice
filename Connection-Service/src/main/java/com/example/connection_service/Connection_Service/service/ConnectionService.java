package com.example.connection_service.Connection_Service.service;

import com.example.connection_service.Connection_Service.client.ConnectionUser;
import com.example.connection_service.Connection_Service.context.UserContext;
import com.example.connection_service.Connection_Service.entity.ConnectionEntity;
import com.example.connection_service.Connection_Service.entity.UserEntity;
import com.example.connection_service.Connection_Service.entity.enums.Status;
import com.example.connection_service.Connection_Service.repository.ConnectionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {
    private final ConnectionsRepository connectionRepository;
    private final ConnectionUser connectionUser;

    public ResponseEntity<?> sendConnectionRequest(Long id) {


        String email = UserContext.getEmail();
        try {
            UserEntity userEntity = connectionUser.findUser(email);
            UserEntity  userEntity2 = connectionUser.findById(id);
            log.info("User found: {}", userEntity.getId());
            if(userEntity2 == null){
                return ResponseEntity.badRequest().body("User not found whom You want to follow");
            }
            if (userEntity.getId().equals(id)) {
                return ResponseEntity.badRequest().body("You cannot send a connection request to yourself");
            }
            if(connectionRepository.existsByFollowerIdAndFollowingId(userEntity.getId(), id)){
                return ResponseEntity.badRequest().body("Connection request already sent");
            }

            ConnectionEntity connectionEntity = ConnectionEntity.builder().status(Status.PENDING).followerId(userEntity.getId()).followingId(id).build();
            connectionRepository.save(connectionEntity);

        }catch (Exception e){
            log.error("User not found: {}", e.getMessage());
            return ResponseEntity.badRequest().body("User not found "+e.getMessage());
        }
        return ResponseEntity.ok("Connection request sent successfully");
    }

    public ResponseEntity<?> acceptConnectionRequest(Long id) {
        String email = UserContext.getEmail();
        try {
            UserEntity userEntity = connectionUser.findUser(email);
            log.info("User found: {}", userEntity.getId());
            ConnectionEntity connectionEntity = connectionRepository.findByFollowerIdAndFollowingId(id, userEntity.getId());
            if (connectionEntity == null) {
                return ResponseEntity.badRequest().body("Connection request not found");
            }
            connectionEntity.setStatus(Status.ACCEPTED);
            connectionRepository.save(connectionEntity);
        }catch (Exception e){
            log.error("User not found: {}", e.getMessage());
            return ResponseEntity.badRequest().body("User not found "+e.getMessage());
        }
        return ResponseEntity.ok("Connection request accepted successfully");
    }

}
