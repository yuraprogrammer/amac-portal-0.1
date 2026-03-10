package yurii.kulyk.amacportal.service;

import yurii.kulyk.amacportal.entity.Tenant;
import yurii.kulyk.amacportal.entity.User;
import yurii.kulyk.amacportal.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class UserService {

    @Inject
    UserRepository repo;

    @Inject
    TenantService tenantService;

    public User find(UUID id) {
        return repo.find(id);
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public User create(User entity) {
        Tenant tenantRef = tenantService.getCurrent();
        entity.setTenant(tenantRef);
        return repo.save(entity);
    }

    public User update(UUID id, User entity) {
        User existing = repo.find(id);
        existing.setEmail(entity.getEmail());
        existing.setRoles(entity.getRoles());
        existing.setPasswordHash(entity.getPasswordHash());
        return repo.update(entity);
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public void delete(UUID id) {
        repo.delete(id);
    }
}
