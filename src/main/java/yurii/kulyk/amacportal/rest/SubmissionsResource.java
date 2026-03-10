package yurii.kulyk.amacportal.rest;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import yurii.kulyk.amacportal.dto.SubmissionDTO;
import yurii.kulyk.amacportal.dto.SubmissionMultipart;
import yurii.kulyk.amacportal.entity.Submission;
import yurii.kulyk.amacportal.mapper.SubmissionMapper;
import yurii.kulyk.amacportal.service.SubmissionService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Path("/submissions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class SubmissionsResource {

    @Inject
    SubmissionService service;

    @Inject
    SubmissionMapper  mapper;

    @GET
    public List<Submission> findAll() {
        return service.findAll();
    }

    @GET
    @Path("/by-id/{id}")
    public Submission find(@PathParam("id") UUID id) {
        return service.find(id);
    }

    @POST
    @Path("/old-resourse")
    public Submission create(Submission entity) {
        return service.create(entity);
    }

    @PUT
    @Path("/{id}")
    public Submission update(@PathParam("id") UUID id, Submission entity) {
        return service.update(id, entity);
    }

    @DELETE
    public void delete(UUID id) {
        service.delete(id);
    }

    @POST
    @Path("/{assignmentId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    public Response submit(@MultipartForm SubmissionMultipart form) throws IOException {

        // Получаем имя файла из заголовка Content-Disposition
        String fileName = form.file.getHeaders()
                .getFirst("Content-Disposition")
                .replaceFirst("(?i)^.*filename=\"([^\"]+)\".*$", "$1");

        // Получаем InputStream содержимого
        InputStream fileStream = form.file.getBody(InputStream.class, null);

        Submission submission = service.submit(
                form.assignmentId,
                form.studentId,
                fileName,
                fileStream,
                form.comment
        );

        return Response.status(Response.Status.CREATED)
                .entity(SubmissionDTO.from(submission))
                .build();
    }

    private UUID getCurrentStudentId() {
        // TODO: позже из security context
        return UUID.fromString("00000000-0000-0000-0000-000000000001");
    }
}
