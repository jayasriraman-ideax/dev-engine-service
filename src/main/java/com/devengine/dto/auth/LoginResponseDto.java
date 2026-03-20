package com.devengine.dto.auth;

import lombok.Data;

@Data
public class LoginResponseDto {
    String userId;

    String status;

    String error;
}
