package yurii.kulyk.amacportal.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LessonCreateDTO {
    private String title;
    private String content;
    private UUID moduleId;
}
