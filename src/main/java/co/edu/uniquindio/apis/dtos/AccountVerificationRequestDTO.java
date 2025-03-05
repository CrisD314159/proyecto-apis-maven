package co.edu.uniquindio.apis.dtos;

public record AccountVerificationRequestDTO (
        String email,
        int verificationCode
) {

}