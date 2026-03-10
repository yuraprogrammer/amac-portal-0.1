package yurii.kulyk.amacportal.service;

import yurii.kulyk.amacportal.entity.AiEvaluation;
import yurii.kulyk.amacportal.repository.AiEvaluationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class AiEvaluationService  {

    @Inject
    AiEvaluationRepository repo;
    @Inject
    TenantService tenantService;

    public AiEvaluation find(UUID id) {
        return repo.find(id);
    }

    public List<AiEvaluation> findAll() {
        return repo.findAll();
    }

    public AiEvaluation create(AiEvaluation entity) {
        entity.setTenant(tenantService.getCurrent());
        return repo.save(entity);
    }

    public AiEvaluation update(UUID id, AiEvaluation entity) {
        AiEvaluation existing = find(id);
        existing.setFeedback(entity.getFeedback());
        existing.setScore(entity.getScore());
        existing.setSubmission(entity.getSubmission());
        return repo.update(entity);
    }

    public void delete(UUID id) {
        repo.delete(id);
    }
}
