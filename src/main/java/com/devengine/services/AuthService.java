package com.devengine.services;

import com.devengine.dto.auth.*;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    RegisterResponseDto register(RegisterRequestDTO request) throws Exception;

    LoginResponseDto login(LoginRequestDTO request) throws Exception;

    ForgetPassResponseDto forgetPassword(ForgetPassRequestDto request) throws Exception;
}
