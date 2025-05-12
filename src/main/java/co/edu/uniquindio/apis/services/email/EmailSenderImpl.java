package co.edu.uniquindio.apis.services.email;

import co.edu.uniquindio.apis.dtos.EmailDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@ApplicationScoped
public class EmailSenderImpl implements EmailSenderInteface{
    private static final Logger log = LoggerFactory.getLogger(EmailSenderImpl.class);


    @Inject
    ObjectMapper objectMapper; // Inyecta Jackson ObjectMapper

    @Override
    public boolean sendEmail(EmailDTO email) {
        System.out.println("Sending email...");
        Resend resend = new Resend("re_jmpY1Ypo_4bKy668hor3YDtfBY9GokUKM");

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Example <example@resend.dev>")
                .to(email.getTo())
                .subject("Demo Email")
                .html("<strong>" + email.getBody() + "</strong>")
                .build();

        try {
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println(data.getId());
            return true;
        } catch (ResendException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
