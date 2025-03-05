package co.edu.uniquindio.apis.mappers.exceptionMappers;

import co.edu.uniquindio.apis.dtos.error.ErrorResponseDTO;
import co.edu.uniquindio.apis.exceptions.ValidationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException e) {
        var response = new ErrorResponseDTO("Validation Error", e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }
}
