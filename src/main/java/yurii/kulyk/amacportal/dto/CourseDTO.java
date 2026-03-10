package yurii.kulyk.amacportal.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Data
public class CourseDTO {
    public UUID id;
    public String title;
    public String description;
}
