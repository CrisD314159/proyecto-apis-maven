package co.edu.uniquindio.apis.dtos;

import jakarta.validation.constraints.Email;

public record LoginRequestDTO (
        @Email
        String email,
        String password
) {
}