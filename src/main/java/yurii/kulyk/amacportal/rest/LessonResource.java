package yurii.kulyk.amacportal.rest;

import yurii.kulyk.amacportal.dto.LessonCreateDTO;
import yurii.kulyk.amacportal.dto.LessonDTO;
import yurii.kulyk.amacportal.service.LessonService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

@Path("/lessons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class LessonResource  {

    @Inject LessonService service;

    @GET
    @Path("/by-module/{moduleId}")
    public List<LessonDTO> byModule(@PathParam("moduleId") UUID moduleId) {
        return service.byModule(moduleId);
    }

    @POST
    public LessonDTO create(LessonCreateDTO dto) {
        return service.create(dto);
    }
}
