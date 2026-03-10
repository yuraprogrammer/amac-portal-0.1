package yurii.kulyk.amacportal.repository;

import yurii.kulyk.amacportal.entity.*;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class LearningMaterialRepository extends TenantAwareRepository<LearningMaterial>{

    @Override
    protected Class<LearningMaterial> type() {
        return LearningMaterial.class;
    }

    public List<LearningMaterial> findForStudent(Course course, User user, Tenant tenant){
        return em.createQuery("""
            select lm from LearningMaterial lm, CourseMember cm
            where 
                cm.user = :user
                and cm.course = :course
                and cm.role = :role
                and lm.tenant = :tenant
        """, LearningMaterial.class)
                .setParameter("course", course)
                .setParameter("user", user)
                .setParameter("role", UserRole.STUDENT)
                .setParameter("tenant", tenant)
                .getResultList();
    }
}
