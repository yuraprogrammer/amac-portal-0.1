package yurii.kulyk.amacportal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "learning_metrics")
public class LearningMetric extends TenantAwareEntity {

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Course course;

    @Column(nullable = false)
    private String metricKey;

    @Column(nullable = false)
    private double metricValue;

    public LearningMetric() {
    }

}

