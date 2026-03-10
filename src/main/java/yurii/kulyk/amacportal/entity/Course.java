package yurii.kulyk.amacportal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "courses")
public class Course extends TenantAwareEntity {

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    public Course() {
    }

}

