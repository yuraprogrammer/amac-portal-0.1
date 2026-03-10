package yurii.kulyk.amacportal.repository;

import yurii.kulyk.amacportal.entity.AiEvaluation;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AiEvaluationRepository extends TenantAwareRepository<AiEvaluation>{
    @Override
    protected Class<AiEvaluation> type() {
        return AiEvaluation.class;
    }
}
