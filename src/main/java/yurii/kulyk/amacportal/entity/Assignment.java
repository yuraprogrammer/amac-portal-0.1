package yurii.kulyk.amacportal.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "assignments")
public class Assignment extends TenantAwareEntity {

    @ManyToOne(optional = false)
    private Lesson lesson;

    @Column(nullable = false)
    private String title;

    @Column(length = 5000)
    private String description;

    @Column
    private Instant dueAt;

    @Enumerated(EnumType.STRING)
    private AssignmentType type;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }


}

