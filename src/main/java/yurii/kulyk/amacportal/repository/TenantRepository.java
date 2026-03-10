package yurii.kulyk.amacportal.repository;

import yurii.kulyk.amacportal.entity.Tenant;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TenantRepository extends BaseRepository<Tenant> {

    public TenantRepository() {
        super(Tenant.class);
    }

}

