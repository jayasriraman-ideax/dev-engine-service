package com.devengine.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequestDTO {

    private String userName;

    private String email;

    private String password;

    private String mode;

    private String otp;

}
