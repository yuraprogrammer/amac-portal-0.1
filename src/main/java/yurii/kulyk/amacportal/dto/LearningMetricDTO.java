package yurii.kulyk.amacportal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LearningMetricDTO {
    public UUID id;
    public UUID userId;
    public UUID courseId;
    public String metricKey;
    public double metricValue;
}

