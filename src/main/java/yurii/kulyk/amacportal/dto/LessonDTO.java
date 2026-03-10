package yurii.kulyk.amacportal.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LessonDTO {
    public UUID id;
    public UUID moduleId;
    public String title;
    public String content;
}

