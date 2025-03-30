package co.edu.uniquindio.apis.exceptions;

import java.util.logging.Logger;

public class UnauthorizedException extends RuntimeException {
    private static final Logger LOG = Logger.getLogger(UnauthorizedException.class.getName());
    public UnauthorizedException(String message) {
        super(message);
        LOG.warning("UnauthorizedException: " + message);
    }
}
