package yurii.kulyk.amacportal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LearningMaterialDTO {
    public UUID id;
    public UUID lessonId;
    public String type;
    public String url;
}

