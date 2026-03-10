package yurii.kulyk.amacportal.rest.student;

import yurii.kulyk.amacportal.dto.CourseCreateDTO;
import yurii.kulyk.amacportal.dto.CourseDTO;
import yurii.kulyk.amacportal.dto.CourseUpdateDTO;
import yurii.kulyk.amacportal.entity.Tenant;
import yurii.kulyk.amacportal.entity.User;
import yurii.kulyk.amacportal.repository.CourseRepository;
import yurii.kulyk.amacportal.service.CourseService;
import yurii.kulyk.amacportal.service.UserService;

import javax.ejb.ObjectNotFoundException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.UUID;

@Path("/student/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CourseResource {

    @Inject
    CourseService courseService;

    @Context
    SecurityContext securityContext;

    @Inject
    UserService userService;

    @GET
    public List<CourseDTO> getCourses() {
        String login = securityContext.getUserPrincipal().getName();
        User user = userService.findByEmail(login);
        return courseService.findForStudent(user);
    }

    @GET
    @Path("/{id}")
    public CourseDTO getCourse(@PathParam("id") UUID id) throws ObjectNotFoundException {
        String login = securityContext.getUserPrincipal().getName();
        User user = userService.findByEmail(login);
        return courseService.findForStudentById(user, id);
    }
    @POST
    public CourseDTO create(CourseCreateDTO dto) {
        return courseService.create(dto);
    }

    @PUT
    @Path("/{id}")
    public CourseDTO update(@PathParam("id") UUID id, CourseUpdateDTO dto) {
        return courseService.update(id, dto);
    }
}
