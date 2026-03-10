package yurii.kulyk.amacportal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "content_embeddings")
public class ContentEmbedding extends TenantAwareEntity {

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private UUID contentId;

    @Column(nullable = false, length = 4000)
    private String vector; // stored as JSON / array / text

    public ContentEmbedding() {
    }

}

