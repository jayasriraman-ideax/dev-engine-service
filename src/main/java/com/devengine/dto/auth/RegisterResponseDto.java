package com.devengine.dto.auth;

import lombok.Data;

@Data
public class RegisterResponseDto {
    String userId;

    String message;

    String status;

    String error;
}
