package com.example.connection_service.Connection_Service.controller;

import com.example.connection_service.Connection_Service.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/connection")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping("/sendConnectionRequest/{id}")
    public ResponseEntity<?> sendConnectionRequest(@PathVariable Long id) {
        return connectionService.sendConnectionRequest(id);
    }
    @PostMapping("/acceptConnectionRequest/{id}")
    public ResponseEntity<?> acceptConnectionRequest(@PathVariable Long id) {
        return connectionService.acceptConnectionRequest(id);
    }
    @PostMapping("/rejectConnectionRequest/{id}")
    public ResponseEntity<?> rejectConnectionRequest(@PathVariable Long id) {
        return connectionService.rejectConnectionRequest(id);
    }


}
