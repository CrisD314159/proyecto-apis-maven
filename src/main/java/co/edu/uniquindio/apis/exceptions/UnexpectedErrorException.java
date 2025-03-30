package co.edu.uniquindio.apis.exceptions;

import java.util.logging.Logger;

public class UnexpectedErrorException extends RuntimeException {
    private static final Logger LOG = Logger.getLogger(UnexpectedErrorException.class.getName());
    public UnexpectedErrorException(String message) {
        super(message);
        LOG.warning("UnexpectedErrorException: " + message);
    }
}
