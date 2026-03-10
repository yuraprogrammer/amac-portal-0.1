package yurii.kulyk.amacportal.rest;

import yurii.kulyk.amacportal.entity.LearningMetric;
import yurii.kulyk.amacportal.service.LearningMetricService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/learning_metrics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class LearningMetricResource {

    @Inject
    LearningMetricService service;

    @GET
    public Response findAll() {
        return Response.ok(service.findAll()).build();
    }

    @GET
    public Response find(UUID id) {
        return Response.ok(service.find(id)).build();
    }

    @POST
    public Response create(LearningMetric entity) {
        return Response.ok(service.create(entity)).build();
    }

    @PUT
    public Response update(UUID id, LearningMetric entity) {
        return Response.ok(service.update(id, entity)).build();
    }

    @DELETE
    public void delete(UUID id) {
        service.delete(id);
    }
}
