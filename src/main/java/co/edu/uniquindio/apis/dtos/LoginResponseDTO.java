package co.edu.uniquindio.apis.dtos;

public record LoginResponseDTO (
        String token,
        String refreshToken
) {
}

