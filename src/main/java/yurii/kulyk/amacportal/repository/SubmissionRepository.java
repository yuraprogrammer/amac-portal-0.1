package yurii.kulyk.amacportal.repository;

import yurii.kulyk.amacportal.entity.Assignment;
import yurii.kulyk.amacportal.entity.Submission;
import yurii.kulyk.amacportal.entity.User;
import yurii.kulyk.amacportal.service.TenantService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class SubmissionRepository extends TenantAwareRepository<Submission>{
    @Inject
    private TenantService tenantService;


    @Inject
    public SubmissionRepository() {
        super();
    }

    @Override
    protected Class<Submission> type() {
        return Submission.class;
    }

    public Optional<Submission> findByAssignmentAndStudent(
            Assignment assignment,
            User student
    ) {
        return em.createQuery("""
                select s from Submission s
                where s.assignment = :assignment
                  and s.user = :student
                  and s.tenant = :tenant
                """, Submission.class)
                .setParameter("assignment", assignment)
                .setParameter("student", student)
                .setParameter("tenant", tenantService.getCurrent())
                .getResultStream()
                .findFirst();
    }

    public Submission persist(Submission submission) {
        em.persist(submission);
        return submission;
    }
}
