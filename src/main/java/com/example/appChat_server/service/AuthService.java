package com.example.appChat_server.service;

import com.example.appChat_server.dto.UserRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> registerUser(String email);
    ResponseEntity<?> confirmRegisterUser(UserRequest userRequest);
}
