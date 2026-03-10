package yurii.kulyk.amacportal.repository;

import yurii.kulyk.amacportal.entity.Assignment;
import yurii.kulyk.amacportal.entity.ContentEmbedding;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContentEmbeddingRepository extends TenantAwareRepository<ContentEmbedding> {

    @Override
    protected Class<ContentEmbedding> type() {
        return ContentEmbedding.class;
    }
}
