package co.edu.uniquindio.apis.services.security;

import java.util.HashSet;
import java.util.Set;

public interface JWTService {

    String generateToken(String email, HashSet<String> roles, Long expiration);
    String generateUserToken(String email);
    String generateAdminToken(String email);
    String generateProfessorToken(String email);


}
