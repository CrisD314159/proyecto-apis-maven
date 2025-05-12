package co.edu.uniquindio.apis.services.email;

import co.edu.uniquindio.apis.dtos.EmailDTO;

public interface EmailSenderInteface {
    boolean sendEmail(EmailDTO email);
}
