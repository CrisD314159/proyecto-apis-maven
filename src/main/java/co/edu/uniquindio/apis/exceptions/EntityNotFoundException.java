package co.edu.uniquindio.apis.exceptions;

import java.util.logging.Logger;

public class EntityNotFoundException extends RuntimeException {
    private static final Logger LOG = Logger.getLogger(EntityNotFoundException.class.getName());
    public EntityNotFoundException(String message) {
        super(message);
        LOG.warning("EntityNotFoundException: " + message);
    }
}