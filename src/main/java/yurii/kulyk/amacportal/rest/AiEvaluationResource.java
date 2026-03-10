package yurii.kulyk.amacportal.rest;

import yurii.kulyk.amacportal.dto.AiEvaluationDTO;
import yurii.kulyk.amacportal.mapper.AiEvaluationMapper;
import yurii.kulyk.amacportal.service.AiEvaluationService;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/ai_evals")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AiEvaluationResource {

    @Inject
    AiEvaluationService service;

    @Inject
    AiEvaluationMapper mapper;

    @GET
    @RolesAllowed({"ADMIN", "TEACHER", "STUDENT"})
    public List<AiEvaluationDTO> findAll() {
        return service.findAll().stream().map(mapper::toDTO).toList();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "TEACHER", "STUDENT"})
    public AiEvaluationDTO find(@PathParam("id") UUID id) {
        return mapper.toDTO(service.find(id));
    }

    @POST
    @RolesAllowed({"ADMIN", "TEACHER"})
    public Response create(AiEvaluationDTO dto) {
        var create = service.create(mapper.toEntity(dto));
        return Response.status(Response.Status.CREATED).entity(mapper.toDTO(create)).build();
    }

    @PUT
    @RolesAllowed({"ADMIN", "TEACHER"})
    public AiEvaluationDTO update(UUID id, AiEvaluationDTO dto) {
        return mapper.toDTO(service.update(id, mapper.toEntity(dto)));
    }

    @DELETE
    @RolesAllowed({"ADMIN"})
    public void delete(UUID id) {
        service.delete(id);
    }
}
