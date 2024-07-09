package com.example.appChat_server.config;

import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
public class OTPConfig {

    private final Map<String, String> otpMap = new HashMap<String, String>();
    private final SecureRandom secureRandom = new SecureRandom();

    public String generateOTP(String email) {
        String otp = String.valueOf(100000 + secureRandom.nextInt(900000));
        System.out.println("OTP generated: " + otp);
        otpMap.put(email, otp);
        return otp;
    }

    public void setTimeOutClearOTP(String email) {
        new Thread(() -> {
            try {
                TimeUnit.MINUTES.sleep(3);
                otpMap.remove(email);
                System.out.println("3 minutes finish. OTP removed: " + otpMap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    public boolean OTPValid(String email, String otp) {
        return otpMap.containsKey(email);
    }

    public boolean OTPVerify(String email, String otp) {
        return otpMap.containsKey(email) && otpMap.get(email).equals(otp);
    }
}
