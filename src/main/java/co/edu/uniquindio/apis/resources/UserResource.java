package co.edu.uniquindio.apis.resources;

import co.edu.uniquindio.apis.dtos.AccountVerificationRequestDTO;
import co.edu.uniquindio.apis.dtos.LoginRequestDTO;
import co.edu.uniquindio.apis.dtos.UserCreateDTO;
import co.edu.uniquindio.apis.dtos.UserUpdateRequestDTO;
import co.edu.uniquindio.apis.exceptions.UnexpectedErrorException;
import co.edu.uniquindio.apis.services.user.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService service;


    @GET
    @Path("/{id}")
    public Response GetSingle(@PathParam("id") Long id) {
        try{
            var user = service.GetUserById(id);
            return Response.ok().entity(user).build();
        }catch (Exception e){
            throw new UnexpectedErrorException("Unable to get user");
        }

    }

    @GET
    public Response GetAll(@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
        try{
            var users = service.GetAllUsers(offset, limit);
            return Response.ok().entity(users).build();
        }catch (Exception e){
            throw new UnexpectedErrorException("Unable to get users");
        }

    }

    @POST
    @PermitAll
    public Response Create(@Valid UserCreateDTO userCreateDTO)
    {
        try{
            var user = service.CreateUser(userCreateDTO);
            return Response.ok().entity(user).build();
        }catch (Exception e){
            throw new UnexpectedErrorException("Unable to create user");
        }

    }

    @PUT
    public Response UpdateUser(@Valid UserUpdateRequestDTO userUpdateDTO)
    {
        try{
            var user = service.UpdateUser(userUpdateDTO);
            return Response.ok().entity(user).build();
        }catch (Exception e){
            throw new UnexpectedErrorException("Unable to update user");
        }

    }

    @DELETE
    @Path("/{id}")
    public Response DeleteUser(@PathParam("id") Long id)
    {
        try{
            var user = service.DeleteUser(id);
            return Response.ok().entity(user).build();
        }catch (Exception e){
            throw new UnexpectedErrorException("Unable to delete user");
        }

    }

    @POST
    @Path("/login")
    public Response LoginRequest (@Valid LoginRequestDTO loginRequest)
    {
        try{
            var login = service.Login(loginRequest);
            return Response.ok().entity(login).build();
        }catch (Exception e){
            throw new UnexpectedErrorException("Unable to login");
        }
    }

    @POST
    @Path("/verify")
    public Response VerifyAccount (@Valid AccountVerificationRequestDTO accountVerificationRequestDTO)
    {
        try{
            service.VerifyAccount(accountVerificationRequestDTO);
            return Response.ok().build();
        }catch (Exception e){
            throw new UnexpectedErrorException("Unable to verify account");
        }
    }



}
