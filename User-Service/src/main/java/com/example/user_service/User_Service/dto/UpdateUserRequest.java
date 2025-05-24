package com.example.user_service.User_Service.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String email;
    private String name;
}