package yurii.kulyk.amacportal.service;

import org.eclipse.microprofile.jwt.JsonWebToken;
import yurii.kulyk.amacportal.entity.Tenant;
import yurii.kulyk.amacportal.repository.TenantRepository;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;
import java.util.UUID;

@ApplicationScoped
public class TenantService {

    //@Inject
    private JsonWebToken jwt;

    //@Inject
    //TenantRepository tenantRepository;

    @PersistenceContext(unitName = "amacPU")
    private EntityManager em;

    private final Tenant defaultTenant = new Tenant(
            UUID.fromString("00000000-0000-0000-0000-000000000000"),
            "Default Tenant"
    );

    /**
     * Возвращает текущий Tenant.
     * Если JWT отсутствует, возвращает дефолтного tenant.
     */
    public Tenant getCurrent() {
        if (jwt == null) {
            return defaultTenant;
        }

        UUID id = parseUUID(getClaimSafe("tenantId"));
        String name = getClaimSafe("tenantName");
        String role = getClaimSafe("role");

        // Если нет claim, возвращаем дефолтное поле
        //return new Tenant(id != null ? id : defaultTenant.getId(), name != null ? name : defaultTenant.getName());
        return em.getReference(Tenant.class, UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    private String getClaimSafe(String claimName) {
        try {
            Object claim = jwt.getClaim(claimName);
            return claim != null ? claim.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private UUID parseUUID(String idStr) {
        try {
            return idStr != null ? UUID.fromString(idStr) : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
