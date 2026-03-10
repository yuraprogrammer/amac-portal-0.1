package yurii.kulyk.amacportal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ContentEmbeddingDTO {
    public UUID id;
    public String contentType;
    public UUID contentId;
    public String vector;
}
