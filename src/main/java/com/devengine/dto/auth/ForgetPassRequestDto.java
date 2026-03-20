package com.devengine.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ForgetPassRequestDto {
    String loginId;

    String email;

    String mode;

    String otp;
}
