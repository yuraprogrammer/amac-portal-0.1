package yurii.kulyk.amacportal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "lessons")
public class Lesson extends TenantAwareEntity {

    @ManyToOne(optional = false)
    private Module module;

    @Column(nullable = false)
    private String title;

    @Column(length = 5000)
    private String content;

    public Lesson() {
    }

}

