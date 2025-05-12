package co.edu.uniquindio.cucumber;

import co.edu.uniquindio.apis.dtos.UserCreateDTO;
import co.edu.uniquindio.apis.dtos.UserResponseDTO;
import co.edu.uniquindio.apis.services.user.UserService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class UserServiceTests {

    @Inject
    UserService userService;


    @Test
    public void testCreateUser() {
        UserCreateDTO user = new UserCreateDTO("cristiandavidvargas2@icloud.com", "Pass-12345", "Cristian");
        UserResponseDTO result = userService.CreateUser(user);
        Assertions.assertEquals(1, result.id());
    }
}
