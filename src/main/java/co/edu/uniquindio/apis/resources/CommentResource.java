package co.edu.uniquindio.apis.resources;

import co.edu.uniquindio.apis.model.Comment;
import co.edu.uniquindio.apis.model.enums.CommentState;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CommentResource {

    @Inject
    JsonWebToken jwt;

    @Claim(standard = Claims.email)
    String email;

    @POST
    @RolesAllowed({"Professor"})
    public Response createComment(Comment comment) {
        return Response.status(Response.Status.CREATED).entity(comment).build();
    }

    @GET
    @Path("/{id}")
    public Response getCommentById(@PathParam("id") UUID id) {
        Comment comment = new Comment();
        return Response.ok(comment).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateComment(@PathParam("id") UUID id, Comment comment) {
        return Response.ok(comment).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteComment(@PathParam("id") UUID id) {
        return Response.noContent().build();
    }

    @PATCH
    @Path("/resolve/{id}")
    public Response resolveComment(@PathParam("id") UUID id) {
        Comment comment = new Comment();
        comment.setState(CommentState.RESOLVED);
        return Response.ok(comment).build();
    }
}
