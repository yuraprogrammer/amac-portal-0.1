package yurii.kulyk.amacportal.rest;

import yurii.kulyk.amacportal.util.TenantContext;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.util.UUID;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class TenantFilter implements ContainerRequestFilter {

    private static final String HEADER = "X-Tenant-Id";

    @Override
    public void filter(ContainerRequestContext ctx) {
        String value = ctx.getHeaderString(HEADER);
        if (value != null) {
            TenantContext.set(UUID.fromString(value));
        }
    }
}
