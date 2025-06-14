package com.example.notification_service.Notification.Service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowDto {

    private Long followerId;
    private String message;

    public Long  getFollowerId() {
        return followerId;
    }
    public String getMessage() {
        return message;
    }

}