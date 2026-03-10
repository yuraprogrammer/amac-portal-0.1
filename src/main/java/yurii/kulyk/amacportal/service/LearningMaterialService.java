package yurii.kulyk.amacportal.service;

import yurii.kulyk.amacportal.entity.LearningMaterial;
import yurii.kulyk.amacportal.repository.LearningMaterialRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class LearningMaterialService {

    @Inject
    LearningMaterialRepository repo;

    @Inject
    TenantService tenantService;

    public LearningMaterial find(UUID id) {
        return repo.find(id);
    }

    public List<LearningMaterial> findAll() {
        return repo.findAll();
    }

    public LearningMaterial create(LearningMaterial entity) {
        entity.setTenant(tenantService.getCurrent());
        return repo.save(entity);
    }

    public LearningMaterial update(UUID id, LearningMaterial entity) {
        LearningMaterial old = find(id);
        old.setLesson(entity.getLesson());
        old.setType(entity.getType());
        old.setUrl(entity.getUrl());
        return repo.update(old);
    }

    public void delete(UUID id) {
        repo.delete(id);
    }
}
