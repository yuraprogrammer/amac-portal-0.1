package yurii.kulyk.amacportal.repository;

import yurii.kulyk.amacportal.entity.*;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class LearningMetricRepository extends TenantAwareRepository<LearningMetric>{

    @Override
    protected Class<LearningMetric> type() {
        return LearningMetric.class;
    }

    public List<LearningMetric> findForStudent(Course course, User user, Tenant tenant){
        return em.createQuery("""
            select lm from LearningMetric lm, CourseMember cm
            where 
                cm.user = :user
                and cm.course = :course
                and cm.role = :role
                and lm.tenant = :tenant
        """, LearningMetric.class)
                .setParameter("course", course)
                .setParameter("user", user)
                .setParameter("role", UserRole.STUDENT)
                .setParameter("tenant", tenant)
                .getResultList();
    }
}
