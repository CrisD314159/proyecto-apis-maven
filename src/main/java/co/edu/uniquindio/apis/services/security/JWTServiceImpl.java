package co.edu.uniquindio.apis.services.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.Claims;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class JWTServiceImpl implements JWTService{


    @Override
    public String generateToken(String email, HashSet<String> roles, Long expiration) {

        try{
            return Jwt.issuer("https://example.com/issuer")
                    .upn("jdoe@quarkus.io")
                    .groups(roles)
                    .claim(Claims.email.name(), email)
                    .sign();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return "null";

    }

    @Override
    public String generateUserToken(String email) {
        HashSet<String> roles = new HashSet<>(Arrays.asList("User"));
        return generateToken(email, roles, 86400000L);
    }

    @Override
    public String generateAdminToken(String email) {
        HashSet<String> roles = new HashSet<>(Arrays.asList("Admin"));
        return generateToken(email, roles, 43200000L);
    }

    @Override
    public String generateProfessorToken(String email) {
        HashSet<String> roles = new HashSet<>(Arrays.asList("Professor"));
        return generateToken(email, roles, 43200000L);
    }

}
