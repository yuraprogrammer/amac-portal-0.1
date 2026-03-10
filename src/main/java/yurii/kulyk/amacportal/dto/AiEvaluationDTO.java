package yurii.kulyk.amacportal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AiEvaluationDTO {
    public UUID id;
    public UUID submissionId;
    public double score;
    public String feedback;
}

