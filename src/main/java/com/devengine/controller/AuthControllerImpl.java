package com.devengine.controller;

import com.devengine.dto.auth.*;
import com.devengine.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController{

    private AuthService authService;

    public AuthControllerImpl(AuthService authService){
        this.authService = authService;
    }

    @Override
    public ResponseEntity<RegisterResponseDto> register(RegisterRequestDTO request) throws Exception {
        RegisterResponseDto response = authService.register(request);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LoginResponseDto> login(LoginRequestDTO request) throws Exception {
        LoginResponseDto response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ForgetPassResponseDto> forgetPassword(ForgetPassRequestDto request) throws Exception {
        ForgetPassResponseDto response = authService.forgetPassword(request);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ForgetPassResponseDto> getDrives(ForgetPassRequestDto request) throws Exception {
        return null;
    }
}
