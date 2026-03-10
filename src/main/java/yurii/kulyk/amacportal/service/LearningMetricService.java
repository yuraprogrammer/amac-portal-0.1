package yurii.kulyk.amacportal.service;

import yurii.kulyk.amacportal.entity.LearningMetric;
import yurii.kulyk.amacportal.repository.LearningMetricRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class LearningMetricService {

    @Inject
    LearningMetricRepository repo;

    @Inject
    TenantService tenantService;

    public LearningMetric find(UUID id) {
        return repo.find(id);
    }

    public List<LearningMetric> findAll() {
        return repo.findAll();
    }

    public LearningMetric create(LearningMetric entity) {
        entity.setTenant(tenantService.getCurrent());
        return repo.save(entity);
    }

    public LearningMetric update(UUID id, LearningMetric entity) {
        LearningMetric old = find(id);
        old.setCourse(entity.getCourse());
        old.setMetricKey(entity.getMetricKey());
        old.setMetricValue(entity.getMetricValue());
        old.setUser(entity.getUser());
        return repo.update(old);
    }

    public void delete(UUID id) {
        repo.delete(id);
    }
}
