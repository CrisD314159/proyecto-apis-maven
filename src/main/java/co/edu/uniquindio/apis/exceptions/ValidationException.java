package co.edu.uniquindio.apis.exceptions;


import java.util.logging.Logger;

public class ValidationException extends RuntimeException {
    private static  final Logger LOG = Logger.getLogger(ValidationException.class.getName());
    public ValidationException(String message) {
        super(message);
        LOG.warning("Validation Exception: " + message);
    }
}
