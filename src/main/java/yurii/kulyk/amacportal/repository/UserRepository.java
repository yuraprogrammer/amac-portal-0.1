package yurii.kulyk.amacportal.repository;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import yurii.kulyk.amacportal.entity.User;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository extends TenantAwareRepository<User> {

    public User findByEmail(String email) {
        String jpql = "select u from User u where u.email = :email";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    protected Class<User> type() {
        return User.class;
    }

}
