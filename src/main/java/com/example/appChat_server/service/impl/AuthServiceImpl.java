package com.example.appChat_server.service.impl;

import com.example.appChat_server.Repository.UserRepository;
import com.example.appChat_server.config.MailSenderConfig;
import com.example.appChat_server.config.OTPConfig;
import com.example.appChat_server.dto.UserRequest;
import com.example.appChat_server.entity.User;
import com.example.appChat_server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MailSenderConfig mailSenderConfig;
    private final OTPConfig otpConfig;
    private final UserRepository userRepository;

    @Value("${email.content}")
    private String contentOTP;

    @Override
    public ResponseEntity<?> registerUser(String email) {
        String subject = "One-Time Password(OTP) For Your Account Registration";
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
        String otp = otpConfig.generateOTP(email);
        mailSenderConfig.sendMail(email, subject, String.format(contentOTP, otp));
        otpConfig.setTimeOutClearOTP(email);
        return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> confirmRegisterUser(UserRequest userRequest) {
        return null;
    }
}
