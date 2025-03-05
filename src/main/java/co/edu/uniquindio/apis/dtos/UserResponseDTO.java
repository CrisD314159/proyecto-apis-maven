package co.edu.uniquindio.apis.dtos;

import co.edu.uniquindio.apis.model.enums.Role;
import co.edu.uniquindio.apis.model.enums.UserState;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UserResponseDTO(

        @NotBlank(message = "ID cannot be empty")
        Long id,
        String fullName,
        @Email(message = "valid email required")
        String email,
        LocalDate creationDate,
        UserState state,
        Role role
) {}
