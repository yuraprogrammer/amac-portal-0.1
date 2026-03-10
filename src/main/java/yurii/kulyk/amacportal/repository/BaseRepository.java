package yurii.kulyk.amacportal.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

public abstract class BaseRepository<T> {

    @PersistenceContext(unitName = "amacPU")
    protected EntityManager em;

    private final Class<T> type;

    protected BaseRepository(Class<T> type) {
        this.type = type;
    }

    public T find(UUID id) {
        return em.find(type, id);
    }

    public List<T> findAll(UUID tenantId) {
        return em.createQuery(
                "select e from " + type.getSimpleName() + " e where e.tenant.id = :tenant",
                type
        ).setParameter("tenant", tenantId).getResultList();
    }

    public T save(T entity) {
        em.persist(entity);
        return entity;
    }

    public T update(T entity) {
        return em.merge(entity);
    }

    public void delete(UUID id, UUID tenantId) {
        em.createQuery(
                        "delete from " + type.getSimpleName() + " e where e.id = :id and e.tenant.id = :tenant"
                )
                .setParameter("id", id)
                .setParameter("tenant", tenantId)
                .executeUpdate();
    }
}
