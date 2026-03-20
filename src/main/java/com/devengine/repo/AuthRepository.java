package com.devengine.repo;

import com.devengine.dto.auth.RegisterRequestDTO;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class AuthRepository {
    private final Jdbi jdbi;

    public AuthRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public String saveUser(RegisterRequestDTO request, String userId) {
        String sql = "INSERT INTO USERS(USER_ID, USER_NAME, EMAIL, PASSWORD, CREATED_AT) VALUES (?, ?, ?, ?, ?)";

        jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind(0, userId)
                        .bind(1, request.getUserName())
                        .bind(2, request.getEmail())
                        .bind(3, request.getPassword())
                        .bind(4, LocalDateTime.now())
                        .execute()
        );

        return userId; // Return the userId that was inserted
    }

    public boolean emailExist(String email) {

        String sql = "SELECT COUNT(*) FROM users WHERE email = :email";

        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind("email", email)
                        .mapTo(Integer.class)
                        .one() > 0
        );
    }


    public String loginUser(String userId) {
        String sql = "SELECT PASSWORD FROM USERS WHERE USER_ID = ?";
        return jdbi.withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0, userId)
                        .mapTo(String.class)
                        .findOne()
                        .orElse(null)
        );
    }

}
