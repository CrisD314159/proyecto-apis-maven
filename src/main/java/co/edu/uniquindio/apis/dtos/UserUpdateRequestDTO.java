package co.edu.uniquindio.apis.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

public record UserUpdateRequestDTO(

        Long id,

        @NotBlank(message = "El campo es requerido")
        @Size(max = 100, message = "No debe exceder los 100 caracteres")
        String fullName

){
}

