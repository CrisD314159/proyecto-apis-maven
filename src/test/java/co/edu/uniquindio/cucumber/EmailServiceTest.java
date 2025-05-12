package co.edu.uniquindio.cucumber;

import co.edu.uniquindio.apis.dtos.EmailDTO;
import co.edu.uniquindio.apis.services.email.EmailSenderInteface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class EmailServiceTest {

    @Inject
    EmailSenderInteface sender;

    @Test
    public void testEmailService() {
        EmailDTO email = new EmailDTO("cristiandavidvargas2@icloud.com", "TestSubject", "Test Body");
        boolean result = sender.sendEmail(email);
        Assertions.assertTrue(result);
    }
}
