package co.edu.uniquindio.apis.resources;

import co.edu.uniquindio.apis.dtos.CommentCreateDTO;
import co.edu.uniquindio.apis.dtos.CommentUpdateDTO;
import co.edu.uniquindio.apis.exceptions.UnexpectedErrorException;
import co.edu.uniquindio.apis.model.Comment;
import co.edu.uniquindio.apis.model.enums.CommentState;
import co.edu.uniquindio.apis.services.comment.CommentService;
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
import java.util.logging.Logger;

@Path("/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CommentResource {
    private static final Logger LOG = Logger.getLogger(CommentResource.class.getName());

    @Inject
    JsonWebToken jwt;

    @Inject
    CommentService commentService;

    @Claim(standard = Claims.email)
    String email;

    @POST
    @RolesAllowed({"Professor"})
    public Response createComment(CommentCreateDTO comment) {
        try{
            commentService.CreateComment(comment);
            LOG.info("Comment created");
            return Response.status(Response.Status.CREATED).entity(comment).build();
        }catch (Exception e){
            throw new UnexpectedErrorException(e.getMessage());
        }
    }

    @GET
    @Path("/{id}")
    public Response getCommentById(@PathParam("id") Long id) {
        try{
            commentService.GetComment(id);
            return  Response.status(Response.Status.OK).build();
        }catch (Exception e){
            throw new UnexpectedErrorException(e.getMessage());
        }
    }

    @PUT
    public Response updateComment(CommentUpdateDTO comment) {
        try{
            commentService.UpdateComment(comment);
            LOG.info("Comment updated");
            return Response.status(Response.Status.OK).build();
        }catch (Exception e){
            throw new UnexpectedErrorException(e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteComment(@PathParam("id") Long id) {
        try {
            commentService.DeleteComment(id);
            LOG.info("Comment deleted");
            return Response.status(Response.Status.OK).build();
        }catch (Exception e){
            throw new UnexpectedErrorException(e.getMessage());
        }
    }

    @PATCH
    @Path("/resolve/{id}")
    public Response resolveComment(@PathParam("id") Long id) {
        try {
            commentService.ResolveComment(id);
            LOG.info("Comment resolved");
            return Response.ok(Response.Status.OK).build();
        }catch (Exception e){
            throw new UnexpectedErrorException(e.getMessage());
        }
    }
}
