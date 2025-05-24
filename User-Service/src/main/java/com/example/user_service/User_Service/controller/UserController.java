package com.example.user_service.User_Service.controller;

import com.example.user_service.User_Service.dto.UpdateUserRequest;
import com.example.user_service.User_Service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/currentUser")
    public ResponseEntity<?> getCurrentUser(){
        return userService.getCurrentUser();
    }

    // PATCH endpoint for update
    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateRequest){
        return userService.updateUser(updateRequest);
    }

    // DELETE endpoint for delete
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(){
        return userService.deleteUser();
    }
}
