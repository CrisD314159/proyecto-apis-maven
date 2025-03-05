package co.edu.uniquindio.apis.mappers.exceptionMappers;

import co.edu.uniquindio.apis.dtos.error.ErrorResponseDTO;
import co.edu.uniquindio.apis.exceptions.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
    @Override
    public Response toResponse (UnauthorizedException exception){
        var response = new ErrorResponseDTO("Unauthorized", exception.getMessage());
        return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
    }
}
