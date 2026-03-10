package yurii.kulyk.amacportal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "ai_evaluations")
public class AiEvaluation extends TenantAwareEntity {

    @OneToOne(optional = false)
    private Submission submission;

    @Column(nullable = false)
    private double score;

    @Column(length = 5000)
    private String feedback;


}

