package yurii.kulyk.amacportal.dto;

import lombok.Getter;
import org.jboss.resteasy.annotations.providers.multipart.PartType;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Getter
public class SubmissionMultipart {

    @FormParam("assignmentId")
    public UUID assignmentId;

    @FormParam("studentId")
    public UUID studentId;

    @FormParam("comment")
    public String comment;

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public InputPart file;
}
