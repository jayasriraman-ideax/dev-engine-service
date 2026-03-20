package com.devengine.services;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OtpService {

    private static final Map<String, String> otpStorage = new ConcurrentHashMap<>();

    public String generateOtp() {
        int otp = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }

    public void storeOtp(String key, String otp) {
        otpStorage.put(key, otp);
    }

    public boolean validateOtp(String key, String otp) {

        String storedOtp = otpStorage.get(key);

        if (storedOtp == null) {
            return false;
        }

        return storedOtp.equals(otp);
    }

    public void removeOtp(String key) {
        otpStorage.remove(key);
    }
}