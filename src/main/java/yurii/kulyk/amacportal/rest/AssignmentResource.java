package yurii.kulyk.amacportal.rest;

import yurii.kulyk.amacportal.dto.AssignmentCreateDTO;
import yurii.kulyk.amacportal.dto.AssignmentDTO;
import yurii.kulyk.amacportal.entity.Assignment;
import yurii.kulyk.amacportal.service.AssignmentService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/assignments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AssignmentResource{

    @Inject
    AssignmentService service;

    @GET
    @Path("/by-lesson/{id}")
    public List<AssignmentDTO> getAssignmentsByLesson(@PathParam("id") UUID lessonId){
        return service.findByLesson(lessonId);
    }

    @POST
    public AssignmentDTO create(AssignmentCreateDTO dto) {
        return service.create(dto);
    }

    @PUT
    public Response update(UUID id, Assignment entity) {
        return Response.ok(service.update(id, entity)).build();
    }

    @DELETE
    public void delete(UUID id) {
        service.delete(id);
    }
}
