package yurii.kulyk.amacportal.repository;

import yurii.kulyk.amacportal.entity.TenantAwareEntity;
import yurii.kulyk.amacportal.util.TenantContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

public abstract class TenantAwareRepository<T extends TenantAwareEntity> {

    @PersistenceContext(unitName = "amacPU")
    protected EntityManager em;

    protected abstract Class<T> type();

    public List<T> findAll() {
        return em.createQuery(
                        "select e from " + type().getSimpleName() + " e where e.tenant.id = :tenant",
                        type()
                ).setParameter("tenant", TenantContext.get())
                .getResultList();
    }

    public T find(UUID id) {
        return em.createQuery(
                        "select e from " + type().getSimpleName() + " e where e.id = :id and e.tenant.id = :tenant",
                        type()
                ).setParameter("id", id)
                .setParameter("tenant", TenantContext.get())
                .getSingleResult();
    }

    public T save(T e) {
        em.persist(e);
        return e;
    }

    public T update(T e) {
        return em.merge(e);
    }

    public void delete(UUID id) {
        em.createQuery(
                        "delete from " + type().getSimpleName() + " e where e.id = :id and e.tenant.id = :tenant"
                ).setParameter("id", id)
                .setParameter("tenant", TenantContext.get())
                .executeUpdate();
    }
}

