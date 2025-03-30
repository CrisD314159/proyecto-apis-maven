package co.edu.uniquindio.apis.resources;

import co.edu.uniquindio.apis.dtos.CommentDTO;
import co.edu.uniquindio.apis.dtos.ProgramCreateDTO;
import co.edu.uniquindio.apis.dtos.ProgramResponseDTO;
import co.edu.uniquindio.apis.dtos.ProgramUpdateRequestDTO;
import co.edu.uniquindio.apis.exceptions.UnexpectedErrorException;
import co.edu.uniquindio.apis.services.program.ProgramService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.logging.Logger;

@Path("/program")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ProgramResource {

    private static final Logger LOG = Logger.getLogger(ProgramResource.class.getName());

    @Inject
     ProgramService programService;
    @Inject
    JsonWebToken jwt;

    @Claim(standard = Claims.email)
    String email;

    @POST
    @RolesAllowed("User")
    public Response createProgram(ProgramCreateDTO programCreateDTO){
        try{
            var response = programService.createProgram(programCreateDTO);
            LOG.info("Nuevo programa registrado con exito");
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (Exception e){
            throw new UnexpectedErrorException("Unable to get program");
        }

    }

    @GET
    @RolesAllowed({"User", "Admin", "Professor"})
    public Response getAllPrograms(){
        try{
            List<ProgramResponseDTO> programs = programService.getAllPrograms();
            return Response.status(Response.Status.OK).entity(programs).build();
        } catch (Exception e){
            throw new UnexpectedErrorException("Unable to get program");
        }

    }

    @GET
    @Path("/{id}/comments")
    @RolesAllowed({"User", "Admin", "Professor"})
    public Response getComments(@PathParam("id") String id , @QueryParam("offset") int offset, @QueryParam("limit") int limit ){
        try{
            List<CommentDTO> comments = programService.getAllComments(id, offset, limit);
            return Response.status(Response.Status.OK).entity(comments).build();
        } catch (Exception e){
            throw new UnexpectedErrorException("Unable to get program");
        }

    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"User", "Admin", "Professor"})
    public Response getProgramById(@PathParam("id") String id){
        try{
            ProgramResponseDTO program = programService.getById(id);
            return Response.status(Response.Status.OK).entity(program).build();
        } catch (Exception e){
            throw new UnexpectedErrorException("Unable to get program");
        }

    }

    @PUT
    @RolesAllowed({"User", "Admin"})
    public Response updateProgram(ProgramUpdateRequestDTO programUpdateRequestDTO){
        try{
            var response = programService.updateProgram(programUpdateRequestDTO);
            LOG.info("Nuevo programa actualizado con exito");
            return Response.status(Response.Status.OK).entity(response).build();
        } catch (Exception e){
            throw new UnexpectedErrorException("Unable to get program");
        }

    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"User", "Admin"})
    public Response deleteProgram(@PathParam("id")String id){
        try{
            var response = programService.deleteProgram(id);
            LOG.info("Nuevo programa eliminado con exito");
            return Response.status(Response.Status.OK).entity(response).build();
        } catch (Exception e){
            throw new UnexpectedErrorException("Unable to get program");
        }

    }




}

