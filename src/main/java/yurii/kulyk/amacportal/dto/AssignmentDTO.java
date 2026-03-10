package yurii.kulyk.amacportal.dto;

import lombok.Data;
import yurii.kulyk.amacportal.entity.AssignmentType;

import java.time.Instant;
import java.util.UUID;

@Data
public class AssignmentDTO {

    private UUID id;
    private String title;
    private String description;
    private Instant dueAt;
    private AssignmentType type;
}

