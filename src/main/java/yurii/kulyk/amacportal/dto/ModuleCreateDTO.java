package yurii.kulyk.amacportal.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ModuleCreateDTO {
    private String title;
    private Integer position;
    private UUID courseId;
    private int orderIndex;
}
