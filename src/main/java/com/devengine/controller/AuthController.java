package com.devengine.controller;

import com.devengine.dto.auth.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public interface AuthController {
    @Operation(summary = "Register a New User To Dev Engine")
    @ApiResponse(responseCode = "200", description = "User Created Successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Request")
    @PostMapping("/register")
    ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDTO request) throws Exception;

    @Operation(summary = "Login To Dev Engine")
    @ApiResponse(responseCode = "200", description = "Logged-in Successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Request")
    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDTO request) throws Exception;

    @Operation(summary = "Password Reset for Users")
    @ApiResponse(responseCode = "200", description = "Password Reset Successfully")
    @ApiResponse(responseCode = "400", description = "Invalid Request")
    @PostMapping("/forget-pass")
    ResponseEntity<ForgetPassResponseDto> forgetPassword(@RequestBody ForgetPassRequestDto request) throws Exception;

    @Operation(summary = "Fetch all the drivers in the Machine")
    @ApiResponse(responseCode = "200", description = "Drivers Found Successfully")
    @ApiResponse(responseCode = "400", description = "No Drivers Found")
    @GetMapping("/drives")
    ResponseEntity<ForgetPassResponseDto> getDrives(@RequestBody ForgetPassRequestDto request) throws Exception;
}
