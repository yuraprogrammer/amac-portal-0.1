package yurii.kulyk.amacportal.repository;

import yurii.kulyk.amacportal.entity.*;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AssignmentRepository extends TenantAwareRepository<Assignment>{

    @Override
    protected Class<Assignment> type() {
        return Assignment.class;
    }

    public List<Assignment> findByLesson(Lesson lesson, Tenant tenant) {
        return em.createQuery("""
            select a from Assignment a
            where a.lesson = :lesson
              and a.tenant = :tenant
            order by a.dueAt
        """, Assignment.class)
                .setParameter("lesson", lesson)
                .setParameter("tenant", tenant)
                .getResultList();
    }

    public void persist(Assignment assignment){
        em.persist(assignment);
    }

    public Assignment findById(UUID id){
        return em.find(Assignment.class, id);
    }

    public List<Assignment> findForStudent(Course course, User user, Tenant tenant){
        return em.createQuery("""
            select a from Assignment a, CourseMember cm
            where 
                cm.user = :user
                and cm.course = :course
                and cm.role = :role
                and a.tenant = :tenant
        """, Assignment.class)
                .setParameter("course", course)
                .setParameter("user", user)
                .setParameter("role", UserRole.STUDENT)
                .setParameter("tenant", tenant)
                .getResultList();
    }
}
