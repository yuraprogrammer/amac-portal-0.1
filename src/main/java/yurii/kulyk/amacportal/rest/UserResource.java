package yurii.kulyk.amacportal.rest;

import yurii.kulyk.amacportal.entity.User;
import yurii.kulyk.amacportal.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserResource extends BaseResource<User> {

    @Inject
    UserService userService;

    @Override
    protected List<User> findAll() {
        return userService.findAll();
    }

    @Override
    protected User find(UUID id) {
        return userService.find(id);
    }

    @Override
    protected User create(User entity) {
        return userService.create(entity);
    }

    @Override
    protected User update(UUID id, User entity) {
        return userService.update(id, entity);
    }

    @Override
    protected void delete(UUID id) {
        userService.delete(id);
    }
}
