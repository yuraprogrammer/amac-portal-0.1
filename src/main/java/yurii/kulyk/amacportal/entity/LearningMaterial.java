package yurii.kulyk.amacportal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "learning_materials")
public class LearningMaterial extends TenantAwareEntity {

    @ManyToOne(optional = false)
    private Lesson lesson;

    @Column(nullable = false)
    private String type; // VIDEO | PDF | LINK

    @Column(nullable = false, length = 2000)
    private String url;

    public LearningMaterial() {
    }

}

