package co.edu.uniquindio.apis.services.security;

import java.util.Set;

public interface JWTService {

    String generateToken(String email, Set<String> roles, Long expiration);
    String generateUserToken(String email);
    String generateAdminToken(String email);


}
