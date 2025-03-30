package co.edu.uniquindio.apis.resources;

import co.edu.uniquindio.apis.dtos.ExampleCreateDTO;
import co.edu.uniquindio.apis.dtos.ExampleResponseDTO;
import co.edu.uniquindio.apis.services.example.ExampleService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.logging.Logger;

@Path("/examples")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ExampleResource {

    private static final Logger LOG = Logger.getLogger(ExampleResource.class.getName());

    @Inject
    ExampleService exampleService;

    @Inject
    JsonWebToken jwt;

    @Claim(standard = Claims.email)
    String email;

    @POST
    @RolesAllowed({"Professor"})
    public Response createExample(@Valid ExampleCreateDTO exampleCreateDTO) {
        ExampleResponseDTO exampleResponseDTO = exampleService.createExample(exampleCreateDTO);
        LOG.info("Nuevo ejemplo registrado con exito");
        return Response.status(Response.Status.CREATED).entity(exampleResponseDTO).build();
    }


     /* Example request body (JSON)
    {
        "title": "Example 1",
        "description": "The first example",
        "content": "The content of the example",
        "creatorId": "550e8400-e29b-41d4-a716-446655440000",
        "tags": ["Example"],
        "difficulty": "Medium"
    }

    URL: http://localhost:8080/examples
     */


    @GET
    @RolesAllowed({"User", "Admin", "Professor"})
    public List<ExampleResponseDTO> listExamples() {
        return exampleService.listExamples();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"User", "Admin", "Professor"})
    public Response getExampleById(@PathParam("id") Long id) {
        ExampleResponseDTO exampleResponseDTO = exampleService.getExampleById(id);
        if (exampleResponseDTO != null) {
            return Response.ok(exampleResponseDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin", "Professor"})
    public Response updateExample(@PathParam("id") Long id, @Valid ExampleCreateDTO exampleCreateDTO) {
        ExampleResponseDTO exampleResponseDTO = exampleService.updateExample(id, exampleCreateDTO);
        if (exampleResponseDTO != null) {
            LOG.info("Nuevo ejemplo actualizado con exito");
            return Response.ok(exampleResponseDTO).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin", "Professor"})
    public Response deleteExample(@PathParam("id") Long id) {
        boolean deleted = exampleService.deleteExample(id);
        if (deleted) {
            LOG.info("Nuevo ejemplo eliminado con exito");
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
