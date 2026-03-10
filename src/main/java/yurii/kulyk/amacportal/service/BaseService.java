package yurii.kulyk.amacportal.service;

import yurii.kulyk.amacportal.util.TenantContext;

import java.util.List;
import java.util.UUID;

public abstract class BaseService<T> {

    protected UUID tenant() {
        return TenantContext.get();
    }

    public abstract T find(UUID id);
    public abstract List<T> findAll();
    public abstract T create(T entity);
    public abstract T update(UUID id, T entity);
    public abstract void delete(UUID id);
    protected Class<T> type;

    public BaseService(Class<T> type) {
        this.type = type;
    }

    public BaseService() {
    }
}
