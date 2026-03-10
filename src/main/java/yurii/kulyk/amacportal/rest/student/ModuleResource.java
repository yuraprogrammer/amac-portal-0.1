package yurii.kulyk.amacportal.rest.student;

import yurii.kulyk.amacportal.dto.ModuleCreateDTO;
import yurii.kulyk.amacportal.dto.ModuleDTO;
import yurii.kulyk.amacportal.service.ModuleService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/modules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ModuleResource {

    @Inject
    ModuleService service;

    @GET
    @Path("/by-course/{courseId}")
    public List<ModuleDTO> byCourse(@PathParam("courseId") UUID courseId) {
        return service.byCourse(courseId);
    }

    @POST
    public ModuleDTO create(ModuleCreateDTO dto) {
        return service.create(dto);
    }
}
