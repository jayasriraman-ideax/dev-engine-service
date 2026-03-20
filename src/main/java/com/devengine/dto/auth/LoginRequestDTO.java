package com.devengine.dto.auth;

import lombok.Data;

@Data
public class LoginRequestDTO {
    String loginId;

    String password;
}

