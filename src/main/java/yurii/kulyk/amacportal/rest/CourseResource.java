package yurii.kulyk.amacportal.rest;

import yurii.kulyk.amacportal.dto.CourseCreateDTO;
import yurii.kulyk.amacportal.dto.CourseDTO;
import yurii.kulyk.amacportal.dto.CourseUpdateDTO;
import yurii.kulyk.amacportal.entity.User;
import yurii.kulyk.amacportal.service.CourseService;
import yurii.kulyk.amacportal.service.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class CourseResource {

    @Inject
    CourseService courseService;

    @Inject
    UserService userService;

    @GET
    public List<CourseDTO> getAll() {
        return courseService.findAll();
    }

    @GET
    @Path("/student/{id}")
    public List<CourseDTO> getCourseForStudent(@PathParam("id") UUID id) {
        User user = userService.find(id);
        return courseService.findForStudent(user);
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
