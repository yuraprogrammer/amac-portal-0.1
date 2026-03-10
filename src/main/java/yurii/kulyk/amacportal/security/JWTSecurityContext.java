package yurii.kulyk.amacportal.security;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Set;
import java.util.UUID;

public class JWTSecurityContext implements SecurityContext {

    private final Principal principal;
    private final Set<String> roles;
    private final UUID tenantId;
    private final boolean secure;

    public JWTSecurityContext(String subject, Set<String> roles, UUID tenantId, boolean secure) {
        this.principal = () -> subject;
        this.roles = roles;
        this.tenantId = tenantId;
        this.secure = secure;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return roles.contains(role);
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";
    }


}
