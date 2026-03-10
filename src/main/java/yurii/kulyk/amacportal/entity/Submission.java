package yurii.kulyk.amacportal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "submissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"assignment_id", "user_id"}))
@Builder
@AllArgsConstructor
public class Submission extends TenantAwareEntity {

    @ManyToOne(optional = false)
    private Assignment assignment;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @Column(length = 2000)
    private String comment;

    private Instant submittedAt;

    public Submission() {
    }

}

