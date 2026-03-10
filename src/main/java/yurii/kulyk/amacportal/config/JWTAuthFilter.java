package yurii.kulyk.amacportal.config;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.jwt.JsonWebToken;
import yurii.kulyk.amacportal.security.JWTSecurityContext;
import yurii.kulyk.amacportal.util.TenantContext;

import java.util.Set;
import java.util.UUID;

@Provider
@Priority(Priorities.AUTHENTICATION)
@ApplicationScoped
public class JWTAuthFilter implements ContainerRequestFilter {

    //@Inject
    private JsonWebToken jwt;

    @Override
    public void filter(ContainerRequestContext ctx) {
        if (jwt == null || jwt.getRawToken() == null) {
            return; // unauthenticated request
        }

        String subject = jwt.getSubject();
        Set<String> roles = jwt.getGroups();
        UUID tenantId = UUID.fromString(jwt.getClaim("tenant_id"));

        SecurityContext original = ctx.getSecurityContext();
        ctx.setSecurityContext(new JWTSecurityContext(subject, roles, tenantId, original.isSecure()));

        TenantContext.set(tenantId);
    }
}


