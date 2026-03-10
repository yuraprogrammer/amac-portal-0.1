package yurii.kulyk.amacportal.rest;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import yurii.kulyk.amacportal.dto.LoginRequest;
import yurii.kulyk.amacportal.dto.LoginResponse;
import yurii.kulyk.amacportal.service.AuthService;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @PermitAll
    public Response login(LoginRequest request) {
        String jwt = authService.login(request.username, request.password);
        return Response.ok(new LoginResponse(jwt)).build();
    }
}
