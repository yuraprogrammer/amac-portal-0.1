package yurii.kulyk.amacportal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    protected UUID id;

    @Column(nullable = false, updatable = false)
    protected Instant createdAt = Instant.now();

    @Column(nullable = false)
    protected Instant updatedAt = Instant.now();

    public BaseEntity() {}

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

}
