package yurii.kulyk.amacportal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "course_members",
        uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "user_id"}))
public class CourseMember extends TenantAwareEntity {

    @ManyToOne(optional = false)
    private Course course;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private String role; // INSTRUCTOR | STUDENT


    public CourseMember() {
    }

}
