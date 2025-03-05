package co.edu.uniquindio.apis.dtos.error;

import jakarta.validation.constraints.NotBlank;

public record ErrorResponseDTO
        (
                @NotBlank String tipo,
                @NotBlank String descripcion

        ){
}
