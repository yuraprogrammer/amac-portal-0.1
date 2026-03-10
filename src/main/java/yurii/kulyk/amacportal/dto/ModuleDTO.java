package yurii.kulyk.amacportal.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ModuleDTO {
    public UUID id;
    public UUID courseId;
    public String title;
}

