package yurii.kulyk.amacportal.dto;

import lombok.Data;
import yurii.kulyk.amacportal.entity.AssignmentType;

import java.time.Instant;
import java.util.UUID;

@Data
public class AssignmentCreateDTO {
    private String title;
    private UUID lessonId;
    private String description;
    private Instant dueAt;
    private AssignmentType type;
}
