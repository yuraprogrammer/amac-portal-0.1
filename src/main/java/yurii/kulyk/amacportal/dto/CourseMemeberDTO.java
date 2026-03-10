package yurii.kulyk.amacportal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CourseMemeberDTO {
    public UUID id;
    public UUID courseId;
    public UUID userId;
    public String role;
}
