package com.devengine.services;

import com.devengine.constant.DevConstants;
import com.devengine.dto.auth.*;
import com.devengine.repo.AuthRepository;
import com.devengine.utils.GenericEmailSender;
import com.devengine.utils.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final GenericEmailSender genericEmailSender;
    private final OtpService otpService;

    @Override
    public RegisterResponseDto register(RegisterRequestDTO request) {

        RegisterResponseDto response = new RegisterResponseDto();

        try {

            if (request.getUserName().isEmpty()
                    || request.getEmail().isEmpty()
                    || request.getPassword().isEmpty()) {

                response.setStatus(DevConstants.FAILED);
                response.setError("All Fields Are Required");
                return response;
            }

            // STEP 1 : GENERATE OTP
            if ("generateOtp".equalsIgnoreCase(request.getMode())) {

                if (authRepository.emailExist(request.getEmail())) {

                    response.setStatus(DevConstants.FAILED);
                    response.setError("Email Already Exists");
                    return response;
                }

                String adminEmail = Properties.getProperties(DevConstants.ADMIN_EMAIL);
                String subject = Properties.getProperties(DevConstants.ADMIN_EMAIL_SUBJECT);
                String bodyTemplate = Properties.getProperties(DevConstants.ADMIN_EMAIL_BODY);

                String otp = otpService.generateOtp();

                otpService.storeOtp(request.getEmail(), otp);

                String body = bodyTemplate
                        .replace("{userName}", request.getUserName())
                        .replace("{userEmail}", request.getEmail())
                        .replace("{timestamp}", String.valueOf(System.currentTimeMillis()))
                        .replace("{otp}", otp);

                genericEmailSender.sendEmail(adminEmail, adminEmail, subject, body);

                response.setStatus(DevConstants.SUCCESS);
                response.setMessage("OTP Sent To Admin");

                return response;
            }

            // STEP 2 : VALIDATE OTP
            if ("otpValidate".equalsIgnoreCase(request.getMode())) {

                boolean validOtp = otpService.validateOtp(request.getEmail(), request.getOtp());

                if (!validOtp) {

                    response.setStatus(DevConstants.FAILED);
                    response.setError("Invalid or expired OTP");
                    return response;
                }

                otpService.removeOtp(request.getEmail());

                String userId = generateUserId(request);

                String savedUser = authRepository.saveUser(request, userId);

                response.setUserId(savedUser);
                response.setMessage("User Created Successfully");
                response.setStatus(DevConstants.SUCCESS);

                return response;
            }

            response.setStatus(DevConstants.FAILED);
            response.setError("Invalid Mode");

        } catch (Exception e) {

            response.setStatus(DevConstants.FAILED);
            response.setError(e.getMessage());
        }

        return response;
    }

    private String generateUserId(RegisterRequestDTO request) {

        String prefix = request.getUserName()
                .substring(0, Math.min(4, request.getUserName().length()))
                .toUpperCase();

        int random = (int) (Math.random() * 9000) + 1000;

        return prefix + random;
    }

    @Override
    public LoginResponseDto login(LoginRequestDTO request) {

        LoginResponseDto response = new LoginResponseDto();

        try {

            if (request.getLoginId().isEmpty() || request.getPassword().isEmpty()) {

                response.setStatus(DevConstants.FAILED);
                response.setError("All Fields Are Required");
                return response;
            }

            String storedPassword = authRepository.loginUser(request.getLoginId());

            if (storedPassword == null) {

                response.setStatus(DevConstants.FAILED);
                response.setError("User Not Found");
                return response;
            }

            if (request.getPassword().equals(storedPassword)) {

                response.setUserId(request.getLoginId());
                response.setStatus(DevConstants.SUCCESS);

            } else {

                response.setStatus(DevConstants.FAILED);
                response.setError("Invalid Credentials");
            }

        } catch (Exception e) {

            response.setStatus(DevConstants.FAILED);
            response.setError(e.getMessage());
        }

        return response;
    }

    @Override
    public ForgetPassResponseDto forgetPassword(ForgetPassRequestDto request) {

        ForgetPassResponseDto response = new ForgetPassResponseDto();

        try {

            if (request.getEmail().isEmpty()) {

                response.setStatus(DevConstants.FAILED);
                response.setError("Email is required");
                return response;
            }

            // STEP 1 : GENERATE OTP
            if ("otpGenerate".equalsIgnoreCase(request.getMode())) {

                String subject = Properties.getProperties(DevConstants.FORGET_PASSWORD_SUBJECT);
                String bodyTemplate = Properties.getProperties(DevConstants.FORGET_PASSWORD_BODY);

                String otp = otpService.generateOtp();

                otpService.storeOtp(request.getEmail(), otp);

                String body = bodyTemplate
                        .replace("{userName}", request.getEmail())
                        .replace("{otp}", otp)
                        .replace("{timestamp}", String.valueOf(System.currentTimeMillis()));

                String adminEmail = Properties.getProperties(DevConstants.ADMIN_EMAIL);

                genericEmailSender.sendEmail(adminEmail, request.getEmail(), subject, body);

                response.setStatus(DevConstants.SUCCESS);
                response.setMessage("Password reset OTP sent");

                return response;
            }

            // STEP 2 : VALIDATE OTP
            if ("otpValidate".equalsIgnoreCase(request.getMode())) {

                boolean validOtp = otpService.validateOtp(request.getEmail(), request.getOtp());

                if (!validOtp) {

                    response.setStatus(DevConstants.FAILED);
                    response.setError("Invalid or expired OTP");
                    return response;
                }

                otpService.removeOtp(request.getEmail());

                response.setStatus(DevConstants.SUCCESS);
                response.setMessage("OTP verified. You can reset your password");

                return response;
            }

            response.setStatus(DevConstants.FAILED);
            response.setError("Invalid Mode");

        } catch (Exception e) {

            response.setStatus(DevConstants.FAILED);
            response.setError(e.getMessage());
        }

        return response;
    }
}