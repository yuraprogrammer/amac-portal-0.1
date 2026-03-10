package yurii.kulyk.amacportal.util;

import io.smallrye.jwt.build.Jwt;
import yurii.kulyk.amacportal.entity.User;

public class JWTIssuer {

    public static String issue(User user) {
        return Jwt.issuer("amac-portal")
                .subject(user.getId().toString())
                .groups(user.getRoles())
                .claim("tenant_id", user.getTenant().getId().toString())
                .expiresIn(3600)
                .sign();
    }
}

