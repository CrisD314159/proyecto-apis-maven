package co.edu.uniquindio.apis.services.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.Claims;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class JWTServiceImpl implements JWTService{


    @Override
    public String generateToken(String email, Set<String> roles, Long expiration) {

        try{
            return Jwt.issuer("https://example.com/issuer")
                    .subject(email)
                    .groups(roles)
                    .expiresAt(System.currentTimeMillis() + expiration)
                    .sign();
        }catch (Exception e){
            System.out.println(e);
        }

        return "null";

    }

    @Override
    public String generateUserToken(String email) {
        Set<String> roles = new HashSet<>(Arrays.asList("user"));
        return generateToken(email, roles, 86400000L);
    }

    @Override
    public String generateAdminToken(String email) {
        Set<String> roles = new HashSet<>(Arrays.asList("admin", "user"));
        return generateToken(email, roles, 43200000L);
    }

}
