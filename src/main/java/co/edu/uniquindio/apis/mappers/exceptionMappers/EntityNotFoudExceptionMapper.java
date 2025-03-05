package co.edu.uniquindio.apis.mappers.exceptionMappers;

import co.edu.uniquindio.apis.dtos.error.ErrorResponseDTO;
import co.edu.uniquindio.apis.exceptions.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EntityNotFoudExceptionMapper implements ExceptionMapper<EntityNotFoundException> {
    @Override
    public Response toResponse(EntityNotFoundException exception) {
        var response = new ErrorResponseDTO("Entity Not found", exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(response).build();
    }
}
