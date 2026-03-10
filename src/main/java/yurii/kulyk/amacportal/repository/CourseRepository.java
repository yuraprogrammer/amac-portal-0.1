package yurii.kulyk.amacportal.repository;

import javax.ejb.ObjectNotFoundException;
import javax.enterprise.context.ApplicationScoped;

import yurii.kulyk.amacportal.dto.CourseDTO;
import yurii.kulyk.amacportal.entity.Course;
import yurii.kulyk.amacportal.entity.Tenant;
import yurii.kulyk.amacportal.entity.User;
import yurii.kulyk.amacportal.entity.UserRole;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CourseRepository extends TenantAwareRepository<Course> {

    @Override protected Class<Course> type() {
        return Course.class;
    }

    public List<Course> findByTenant(Tenant tenant) {
        return em.createQuery("""
            select c from Course c
            where c.tenant = :tenant
        """, Course.class)
                .setParameter("tenant", tenant)
                .getResultList();
    }

    public void persist(Course course) {
        em.persist(course);
    }

    public Course findById(UUID id) {
        return em.find(Course.class, id);
    }

    public List<Course> findForStudent(User user, Tenant tenant) {
        return em.createQuery("""
            select c
            from Course c, CourseMember cm
            where cm.course = c
              and cm.user = :user
              and cm.role = :role
              and c.tenant = :tenant
        """, Course.class)
                .setParameter("user", user)
                .setParameter("role", UserRole.STUDENT)
                .setParameter("tenant", tenant)
                .getResultList();
    }

    public Course findForStudentById(User user, Tenant tenant, UUID id) throws ObjectNotFoundException {
        List<Course> list = em.createQuery("""
            select c from Course c, CourseMember cm
            where cm.course = c and cm.user = :user and cm.role = :role and c.tenant =:tenant and c.id =:id
        """, Course.class).setParameter("user", user)
                .setParameter("role", UserRole.STUDENT)
                .setParameter("tenant", tenant)
                .setParameter("id", id)
                .getResultList();
            return list.get(0);

    }

}
